package ru.topbun.recipes.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.topbun.recipes.R
import ru.topbun.recipes.domain.entity.DetailRecipeModel


class StepRecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_recipe, container, false)
    }

    companion object {

        private const val BUNDLE_STEP_RECIPE = "bundle_step_recipe"

        fun getInstance(detailRecipe: DetailRecipeModel): StepRecipeFragment{
            val fragment = StepRecipeFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(BUNDLE_STEP_RECIPE, detailRecipe)
            }
            return fragment
        }
    }

}