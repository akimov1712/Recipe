package ru.topbun.recipes.presentation.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.FragmentHomeBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import javax.inject.Inject

class HomeFragment : Fragment() {

    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java] }
    private val adapter by lazy { RecipeAdapter() }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
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
        setRecyclerView()
    }

    private fun observeViewModel(){
        with(binding){
            viewModel.state.observe(viewLifecycleOwner){
                when(it){
                    is HomeState.RecipeList -> {
                        adapter.submitList(it.recipeList)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setAdapter(){
        adapter.setOnRecipeClickListener = {urlFullRecipe, preview ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            startActivity(intent)
        }
        adapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipes.adapter = adapter
    }

    private fun setRecyclerView(){
        with(binding) {
            val layoutManager = rvRecipes.layoutManager

            binding.rvRecipes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager is LinearLayoutManager) {
                        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

                        if (firstVisiblePosition >= 3){
                            btnTop.visibility = View.VISIBLE
                        } else {
                            btnTop.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun setListenersInView(){
        with(binding){
            btnTop.setOnClickListener {
                rvRecipes.smoothScrollToPosition(0)
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}