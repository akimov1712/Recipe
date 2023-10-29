package ru.topbun.recipes.presentation.main.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.FragmentCategoryBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.category.adapter.CategoryAdapter
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter
import javax.inject.Inject


class CategoryFragment : Fragment() {
    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentCategoryBinding? = null
    private val binding: FragmentCategoryBinding
        get() = _binding ?: throw RuntimeException("FragmentCategoryBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java] }
    private val recipeAdapter by lazy{ RecipeAdapter() }
    private val categoryAdapter by lazy{ CategoryAdapter() }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
    }

    private fun setViews(){
        setRecipeAdapter()
        setCategoryAdapter()
        setListenersInView()
    }

    private fun setRecipeAdapter(){
        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            startActivity(intent)
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }

    private fun setCategoryAdapter(){
        categoryAdapter.submitList(getCategoryList())
        categoryAdapter.setOnCategoryClickListener = {
            viewModel.getRecipeByCategory(it)
            binding.tvToolbarRecipeName.text = it
        }
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun setListenersInView(){
        with(binding){
            btnBack.setOnClickListener {
                binding.clCategory.visibility = View.VISIBLE
                binding.clRecipeList.visibility = View.GONE
            }
        }
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is CategoryState.RecipeList -> {
                            recipeAdapter.submitList(it.recipeList)
                            clCategory.visibility = View.GONE
                            clRecipeList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun getCategoryList(): List<String>{
        val categoryList = mutableListOf<String>()
        categoryList.add("Основные блюда")
        categoryList.add("Завтраки")
        categoryList.add("Выпечка и десерты")
        categoryList.add("Паста и пицца")
        categoryList.add("Салаты")
        categoryList.add("Закуски")
        categoryList.add("Супы")
        categoryList.add("Сэндвичи")
        categoryList.add("Ризотто")
        categoryList.add("Соусы и маринады")
        categoryList.add("Напитки")
        categoryList.add("Заготовки")
        categoryList.add("Бульоны")
        return categoryList
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}