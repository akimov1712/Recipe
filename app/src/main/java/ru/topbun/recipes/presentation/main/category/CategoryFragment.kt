package ru.topbun.recipes.presentation.main.category

import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentCategoryBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.main.MainFragmentDirections
import ru.topbun.recipes.presentation.main.category.adapter.CategoryAdapter
import ru.topbun.recipes.utils.findTopNavController

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val viewModel by viewModels<CategoryViewModel>()
    private val recipeAdapter by lazy{ RecipeAdapter() }
    private val categoryAdapter by lazy{ CategoryAdapter() }

    override fun setViews(){
        super.setViews()
        setOnBackPressed()
        binding.tvToolbarRecipeName.text = viewModel.getCategoryTitle()
    }

    private fun setRecipeAdapter(){
        recipeAdapter.setOnRecipeClickListener = { url, preview, id ->
            findTopNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailRecipeFragment(id,url, preview)
            )
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

    private fun setCategoryAdapter(){
        categoryAdapter.setOnCategoryClickListener = {
            viewModel.getRecipeByCategory(it)
            viewModel.saveCategoryTitle(it)
            binding.tvToolbarRecipeName.text = it

        }
        binding.rvCategory.adapter = categoryAdapter
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
                                    progressBar.visibility = View.GONE
                                }
                                is CategoryState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                }
                                is CategoryState.RecipeError -> {
                                    progressBar.visibility = View.INVISIBLE
                                }
                                is CategoryState.CategoryList -> {
                                    categoryAdapter.submitList(it.categoryList)
                                    clCategory.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
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
        private const val EXTRA_CATEGORY = "extraCategory"
    }

}