package ru.topbun.recipes.presentation.main.favorite

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentFavoriteBinding
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.main.search.SearchState

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel>()
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun observeViewModel(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding){
                    with(viewModel){
                        state.collect {
                            when(it){
                                is FavoriteState.ErrorRecipe -> {
                                    tvToolbarName.text = "Избранное 0"
                                    tvNotFound.visibility = View.VISIBLE
                                    recipeAdapter.submitList(emptyList())
                                    progressBar.visibility = View.GONE
                                }
                                is FavoriteState.RecipeList -> {
                                    tvToolbarName.text = "Избранное " + it.recipeList.size
                                    tvNotFound.visibility = View.GONE
                                    recipeAdapter.submitList(it.recipeList)
                                    progressBar.visibility = View.GONE
                                }

                                is FavoriteState.Loading -> {
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
        binding.rvRecipe.adapter = recipeAdapter
    }

    override fun setListenersInView(){}
    override fun setRecyclerViews(){}

}