package ru.topbun.recipes.presentation.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentHomeBinding
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val adapter by lazy { RecipeAdapter() }

    override fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding){
                    viewModel.state.collect{
                        when(it){
                            is HomeState.RecipeList -> {
                                adapter.submitList(it.recipeList)
                                progressBar.visibility = View.GONE
                                tvError.visibility = View.INVISIBLE
                            }
                            is HomeState.Loading -> {
                                progressBar.visibility = View.VISIBLE
                                tvError.visibility = View.INVISIBLE
                            }
                            is HomeState.ErrorRecipe -> {
                                tvError.text = it.message
                                tvError.visibility = View.VISIBLE
                            }
                        }
                    }
                }

            }
        }
    }

    override fun setAdapters() {
        adapter.setOnRecipeClickListener = {urlFullRecipe, preview, id ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            intent.putExtra(DetailRecipeActivity.EXTRA_ID, id)
            startActivity(intent)
        }
        adapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipes.adapter = adapter
    }

    override fun setRecyclerViews() {
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

    override fun setListenersInView(){
        with(binding){
            btnTop.setOnClickListener {
                rvRecipes.scrollToPosition(0)
            }
        }
    }

}