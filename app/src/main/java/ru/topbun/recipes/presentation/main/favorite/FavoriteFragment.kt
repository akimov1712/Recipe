package ru.topbun.recipes.presentation.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.databinding.FragmentFavoriteBinding
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel>()
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner) {
                    when(it){
                        is FavoriteState.ErrorRecipe -> {
                            tvToolbarName.text = "Избранное 0"
                            tvNotFound.visibility = View.VISIBLE
                            recipeAdapter.submitList(emptyList())
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