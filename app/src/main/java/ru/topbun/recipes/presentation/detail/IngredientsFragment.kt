package ru.topbun.recipes.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.topbun.recipes.R
import ru.topbun.recipes.domain.entity.DetailRecipeModel


class IngredientsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ingridients, container, false)
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