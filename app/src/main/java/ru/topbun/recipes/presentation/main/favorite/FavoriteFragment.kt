package ru.topbun.recipes.presentation.main.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentFavoriteBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.detail.DetailRecipeViewModel
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoriteBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java] }
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
    }

    private fun setViews(){
        setListenersInView()
        setAdapter()
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner) {
                    when(it){
                        is FavoriteState.ErrorRecipe -> {
                            tvNotFound.visibility = View.VISIBLE
                        }
                        is FavoriteState.RecipeList -> {
                            tvToolbarName.text = "Избранное " + it.recipeList.size
                            tvNotFound.visibility = View.GONE
                            recipeAdapter.submitList(it.recipeList)
                        }
                    }
                }
            }
        }
    }

    private fun setAdapter(){
        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            startActivity(intent)
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

    private fun setListenersInView(){}


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}