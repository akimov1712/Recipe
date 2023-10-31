package ru.topbun.recipes.presentation.main.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var choiceCategory = "Категория"

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
        getDataFromBundle(savedInstanceState)
        setViews()
        observeViewModel()
    }

    private fun getDataFromBundle(savedInstanceState: Bundle?) {
        choiceCategory = savedInstanceState?.getString(EXTRA_CATEGORY).toString()
        binding.tvToolbarRecipeName.text = choiceCategory
    }

    private fun setViews(){
        setRecipeAdapter()
        setCategoryAdapter()
        setListenersInView()
        setRecyclerView()
    }

    private fun setRecipeAdapter(){
        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview, id ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            intent.putExtra(DetailRecipeActivity.EXTRA_ID, id)
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
            choiceCategory = it
            binding.tvToolbarRecipeName.text = choiceCategory

        }
        binding.rvCategory.adapter = categoryAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_CATEGORY, choiceCategory)
    }

    private fun setListenersInView(){
        with(binding){
            btnBack.setOnClickListener {
                binding.clCategory.visibility = View.VISIBLE
                binding.clRecipeList.visibility = View.GONE
            }
            btnTop.setOnClickListener {
                rvRecipe.smoothScrollToPosition(0)
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

    private fun setRecyclerView(){
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

    private fun getCategoryList(): List<Pair<String, String>>{
        val categoryList = mutableListOf<Pair<String, String>>()
        categoryList.add(Pair("Основные блюда","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/140802212008/160519080709/p_O.jpg.webp"))
        categoryList.add(Pair("Завтраки","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/120213175531/180415114517/p_O.jpg.webp"))
        categoryList.add(Pair("Выпечка и десерты","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/150615095301/150618125006/p_O.jpg.webp"))
        categoryList.add(Pair("Паста и пицца","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/120213175134/1202131752241/p_O.jpg.webp"))
        categoryList.add(Pair("Салаты","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/130725133817/190726123035/p_O.jpg.webp"))
        categoryList.add(Pair("Закуски","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/121017153819/151024131131/p_O.jpg.webp"))
        categoryList.add(Pair("Супы","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/120213181135/130318133553/p_O.jpg.webp"))
        categoryList.add(Pair("Сэндвичи","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/130829212936/130904171008/p_O.jpg.webp"))
        categoryList.add(Pair("Ризотто","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/160822164559/160901074921/p_O.jpg.webp"))
        categoryList.add(Pair("Соусы и маринады","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/150819165228/150828142745/p_O.jpg.webp"))
        categoryList.add(Pair("Напитки","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/120214125404/180820205059/p_O.jpg.webp"))
        categoryList.add(Pair("Заготовки","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/170223141144/210902152756/p_O.jpg.webp"))
        categoryList.add(Pair("Бульоны","https://eda.ru/img/eda/c180x180/s1.eda.ru/StaticContent/Photos/120214131251/140824234958/p_O.jpg.webp"))
        return categoryList
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        private const val EXTRA_CATEGORY = ""
    }

}