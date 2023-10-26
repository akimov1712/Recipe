package ru.topbun.recipes.presentation.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentStepRecipeBinding
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.parcelable
import ru.topbun.recipes.presentation.detail.stepDetailRecipe.StepRecipeAdapter


class StepRecipeFragment : Fragment() {

    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentStepRecipeBinding? = null
    private val binding: FragmentStepRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentStepRecipeBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    private val adapter by lazy { StepRecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        getDataFromBundle()
    }

    private fun getDataFromBundle(){
        val recipe = arguments?.parcelable<DetailRecipeModel>(BUNDLE_STEP_RECIPE)
        adapter.submitList(recipe?.stepRecipeList)
    }

    private fun setViews(){
        setAdapter()
    }

    private fun setAdapter(){
        binding.rvStepRecipe.adapter = adapter
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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