package ru.topbun.recipes.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.databinding.FragmentIngridientsBinding
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.utils.parcelable
import ru.topbun.recipes.presentation.detail.ingrAdapter.IngrAdapter
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class IngredientsFragment : BaseFragment<FragmentIngridientsBinding>(FragmentIngridientsBinding::inflate) {

    private val adapter by lazy { IngrAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
    }

    override fun setListenersInView() {}
    override fun setRecyclerViews() {}
    override fun observeViewModel() {}

    private fun getDataFromBundle(){
        val recipe = arguments?.parcelable<DetailRecipeModel>(BUNDLE_INGR)
        adapter.submitList(recipe?.ingrList)
    }

    override fun setAdapters(){
        binding.rvIngr.adapter = adapter
    }

    companion object {

        private const val BUNDLE_INGR = "bundle_ingr"

        fun getInstance(detailRecipe: DetailRecipeModel): IngredientsFragment{
            val fragment = IngredientsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_INGR, detailRecipe)
            }
            return fragment
        }
    }

}