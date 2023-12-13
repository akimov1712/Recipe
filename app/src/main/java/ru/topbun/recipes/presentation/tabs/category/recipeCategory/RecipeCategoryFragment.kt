package ru.topbun.recipes.presentation.tabs.category.recipeCategory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.FragmentRecipeCategoryBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.recipeAdapter.RecipeAdapter
import ru.topbun.recipes.presentation.tabs.TabsFragmentDirections
import ru.topbun.recipes.presentation.tabs.category.category.CategoryViewModel
import ru.topbun.recipes.utils.findTopNavController

@AndroidEntryPoint
class RecipeCategoryFragment :
    BaseFragment<FragmentRecipeCategoryBinding>(FragmentRecipeCategoryBinding::inflate) {

    private val args by navArgs<RecipeCategoryFragmentArgs>()
    private val viewModel by viewModels<RecipeCategoryViewModel>()
    private val recipeAdapter by lazy{ RecipeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun setViews() {
        super.setViews()
        setData()
    }

    private fun setData(){
        binding.tvToolbarRecipeName.text = args.category
    }

    private fun getData(){
        viewModel.getRecipeByCategory(args.category)
    }

    override fun setListenersInView() {
        super.setListenersInView()
        with(binding){
            btnTop.setOnClickListener {
                rvRecipe.scrollToPosition(0)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
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

    override fun setAdapters() {
        super.setAdapters()
        setRecipeAdapter()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.state.collect{
                    when(it){
                        is RecipeCategoryState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is RecipeCategoryState.RecipeError -> {
                            Toast.makeText(requireContext(), "Ошибка в получении рецептов", Toast.LENGTH_SHORT).show()
                        }
                        is RecipeCategoryState.RecipeList -> {
                            recipeAdapter.submitList(it.recipeList)
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setRecipeAdapter(){
        recipeAdapter.setOnRecipeClickListener = { url, preview, id ->
            findTopNavController().navigate(
                TabsFragmentDirections.actionMainFragmentToDetailRecipeFragment(id,url, preview)
            )
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

}