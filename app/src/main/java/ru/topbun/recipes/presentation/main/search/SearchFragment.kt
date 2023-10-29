package ru.topbun.recipes.presentation.main.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.FragmentSearchBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import javax.inject.Inject


class SearchFragment : Fragment() {
    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java] }
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
        getData()
    }

    private fun getData(){
        val query = binding.editText.text.toString()
        viewModel.getRecipeQuery(query)
    }


    private fun setViews(){
        setListenersInView()
        setTextWatcherInEditText()
        setAdapter()
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is SearchState.ErrorRecipe -> {
                            tvNotFount.visibility = View.VISIBLE
                        }
                        is SearchState.RecipeList -> {
                            tvNotFount.visibility = View.GONE
                            recipeAdapter.submitList(it.recipeList)
                            rvRecipes.scrollToPosition(0)
                        } else -> {}
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
        binding.rvRecipes.adapter = recipeAdapter
    }

    private fun setTextWatcherInEditText(){
        binding.editText.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s.toString().let { viewModel.getRecipeQuery(it) }
                if(s?.isNotBlank() == true){
                    binding.btnClear.visibility = View.VISIBLE
                } else {
                    binding.btnClear.visibility = View.GONE
                    binding.rvRecipes.scrollToPosition(0)
                }
            }
        })
    }

    private fun setListenersInView(){
        with(binding){
            btnClear.setOnClickListener {
                editText.text.clear()
            }
        }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}