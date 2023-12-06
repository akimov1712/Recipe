package ru.topbun.recipes.presentation.detail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.databinding.FragmentStepRecipeBinding
import ru.topbun.recipes.domain.entity.DetailRecipeEntity
import ru.topbun.recipes.utils.parcelable
import ru.topbun.recipes.presentation.detail.stepDetailRecipe.StepRecipeAdapter
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class StepRecipeFragment : BaseFragment<FragmentStepRecipeBinding>(FragmentStepRecipeBinding::inflate) {

    private val adapter by lazy { StepRecipeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
    }

    private fun getDataFromBundle(){
        val recipe = arguments?.parcelable<DetailRecipeEntity>(BUNDLE_STEP_RECIPE)
        adapter.submitList(recipe?.stepRecipeList)
    }

    override fun setListenersInView() {}
    override fun setRecyclerViews() {}
    override fun observeViewModel() {}

    override fun setAdapters(){
        binding.rvStepRecipe.adapter = adapter
    }

    companion object {

        private const val BUNDLE_STEP_RECIPE = "bundle_step_recipe"

        fun getInstance(detailRecipe: DetailRecipeEntity): StepRecipeFragment{
            val fragment = StepRecipeFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_STEP_RECIPE, detailRecipe)
            }
            return fragment
        }
    }

}