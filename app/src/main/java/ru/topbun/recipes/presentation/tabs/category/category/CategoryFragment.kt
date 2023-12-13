package ru.topbun.recipes.presentation.tabs.category.category

import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentCategoryBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.tabs.TabsFragmentDirections
import ru.topbun.recipes.presentation.tabs.category.category.adapter.CategoryAdapter
import ru.topbun.recipes.utils.findTopNavController

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel by viewModels<CategoryViewModel>()
    private val categoryAdapter by lazy{ CategoryAdapter() }

    private fun setCategoryAdapter(){
        categoryAdapter.setOnCategoryClickListener = {
            findNavController().navigate(
                CategoryFragmentDirections.actionCategoryFragmentToRecipeCategoryFragment(it)
            )
        }
        binding.rvCategory.adapter = categoryAdapter
    }

    override fun setAdapters() {
        setCategoryAdapter()
    }

    override fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                with(binding){
                    with(viewModel){
                        state.collect{
                            when(it){
                                is CategoryState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                }
                                is CategoryState.CategoryList -> {
                                    categoryAdapter.submitList(it.categoryList)
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}