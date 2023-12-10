package ru.topbun.recipes.presentation.main.category

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentCategoryBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.OnNavigateToDetailRecipe
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.main.MainFragment
import ru.topbun.recipes.presentation.main.category.adapter.CategoryAdapter

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel by viewModels<CategoryViewModel>()
    private val recipeAdapter by lazy{ RecipeAdapter() }
    private val categoryAdapter by lazy{ CategoryAdapter() }

    private var choiceCategory = "Категория"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle(savedInstanceState)
    }

    private fun getDataFromBundle(savedInstanceState: Bundle?) {
        choiceCategory = savedInstanceState?.getString(EXTRA_CATEGORY).toString()
        binding.tvToolbarRecipeName.text = choiceCategory
    }

    override fun setViews(){
        super.setViews()
        setOnBackPressed()
    }

    private fun setRecipeAdapter(){
        recipeAdapter.setOnRecipeClickListener = {url, preview, id ->
            val parentFragment = parentFragment?.parentFragment
            if (parentFragment is OnNavigateToDetailRecipe){
                (parentFragment as MainFragment).navigateToDetailRecipeFragment(id, url, preview)
            }
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

    private fun setCategoryAdapter(){
        categoryAdapter.setOnCategoryClickListener = {
            viewModel.getRecipeByCategory(it)
            choiceCategory = it
            binding.tvToolbarRecipeName.text = choiceCategory

        }
        binding.rvCategory.adapter = categoryAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_CATEGORY, choiceCategory)
    }

    override fun setListenersInView(){
        with(binding){
            btnBack.setOnClickListener {
                binding.clCategory.visibility = View.VISIBLE
                binding.clRecipeList.visibility = View.GONE
            }
            btnTop.setOnClickListener {
                rvRecipe.scrollToPosition(0)
            }
        }
    }

    override fun setAdapters() {
        setRecipeAdapter()
        setCategoryAdapter()
    }

    override fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                with(binding){
                    with(viewModel){
                        state.collect{
                            when(it){
                                is CategoryState.RecipeList -> {
                                    recipeAdapter.submitList(it.recipeList)
                                    clCategory.visibility = View.GONE
                                    clRecipeList.visibility = View.VISIBLE
                                    progressBar.visibility = View.INVISIBLE
                                }
                                is CategoryState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                }
                                is CategoryState.RecipeError -> {
                                    progressBar.visibility = View.INVISIBLE
                                }
                                is CategoryState.CategoryList -> {
                                    categoryAdapter.submitList(it.categoryList)
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    override fun setRecyclerViews(){
        with(binding) {
            val layoutManager = rvRecipe.layoutManager
            binding.rvRecipe.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun setOnBackPressed() {
        with(binding){
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner) {
                    if (clRecipeList.isVisible){
                        clRecipeList.isVisible = false
                        clCategory.isVisible = true
                        recipeAdapter.submitList(emptyList())
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
        }
    }

    companion object{
        private const val EXTRA_CATEGORY = ""
    }

}