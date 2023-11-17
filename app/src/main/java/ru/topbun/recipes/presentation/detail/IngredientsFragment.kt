package ru.topbun.recipes.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.databinding.FragmentIngridientsBinding
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.parcelable
import ru.topbun.recipes.presentation.detail.ingrAdapter.IngrAdapter

@AndroidEntryPoint
class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngridientsBinding? = null
    private val binding: FragmentIngridientsBinding
        get() = _binding ?: throw RuntimeException("FragmentIngridientsBinding == null")

    private val adapter by lazy { IngrAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngridientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        getDataFromBundle()
    }

    private fun getDataFromBundle(){
        val recipe = arguments?.parcelable<DetailRecipeModel>(BUNDLE_INGR)
        adapter.submitList(recipe?.ingrList)
    }

    private fun setViews(){
        setAdapter()
    }

    private fun setAdapter(){
        binding.rvIngr.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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