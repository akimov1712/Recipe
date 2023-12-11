package ru.topbun.recipes.presentation.main.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentFavoriteBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.main.MainFragmentDirections
import ru.topbun.recipes.utils.findTopNavController

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel>()
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun observeViewModel(){
        viewModel.getFavoriteRecipe()
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
        recipeAdapter.setOnRecipeClickListener = { url, preview, id ->val parentFragment = parentFragment?.parentFragment
            findTopNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailRecipeFragment(id,url, preview)
            )
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

    override fun setListenersInView(){}
    override fun setRecyclerViews(){}

}