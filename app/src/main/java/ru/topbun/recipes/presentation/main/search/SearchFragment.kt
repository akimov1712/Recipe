package ru.topbun.recipes.presentation.main.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentSearchBinding
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel by viewModels<SearchViewModel>()
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData(){
        val query = binding.editText.text.toString()
        viewModel.getRecipeQuery(query)
    }

    override fun setViews(){
        super.setViews()
        setTextWatcherInEditText()
    }

    override fun setRecyclerViews(){
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

    override fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                with(binding){
                    with(viewModel){
                        state.collect{
                            when(it){
                                is SearchState.ErrorRecipe -> {
                                    tvNotFount.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                                is SearchState.RecipeList -> {
                                    tvNotFount.visibility = View.GONE
                                    recipeAdapter.submitList(it.recipeList)
                                    progressBar.visibility = View.GONE
                                }
                                is SearchState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    override fun setAdapters(){
        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview, id ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            intent.putExtra(DetailRecipeActivity.EXTRA_ID, id)
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

    override fun setListenersInView(){
        with(binding){
            btnClear.setOnClickListener {
                editText.text.clear()
            }
            btnTop.setOnClickListener {
                rvRecipes.scrollToPosition(0)
            }
        }
    }

}