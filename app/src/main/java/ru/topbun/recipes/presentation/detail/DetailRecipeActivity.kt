package ru.topbun.recipes.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.ActivityDetailRecipeBinding
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.presentation.base.ViewModelFactory
import javax.inject.Inject

class DetailRecipeActivity : AppCompatActivity() {

    private val component by lazy {(application as App).component}
    private val binding by lazy {ActivityDetailRecipeBinding.inflate(layoutInflater)}

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[DetailRecipeViewModel::class.java] }

    private lateinit var pageAdapter: DetailRecipePageAdapter

    private var recipeId = UNDEFINED_ID
    private var recipeUrl = UNDEFINED_URL
    private var recipePreview = UNDEFINED_PREVIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        getData()
        setViews()
        observeViewModel()
    }

    private fun getData(){
        recipeId = intent.getIntExtra(EXTRA_ID, UNDEFINED_ID)
        recipeUrl = intent.getStringExtra(EXTRA_URL).toString()
        recipePreview = intent.getStringExtra(EXTRA_PREVIEW).toString()

        viewModel.getRecipe(recipeId)
        recipeUrl.let { viewModel.getDetailRecipe(it) }
        Picasso.get().load(recipePreview).into(binding.ivPreview)
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(this@DetailRecipeActivity){
                    when(it){
                        is DetailRecipeState.DetailRecipe -> {
                            setupViewPager(it.detailRecipeItem)
                            tvName.text = it.detailRecipeItem.name
                            tvToolbarName.text = it.detailRecipeItem.name
                            tvCategory.text = "Категория: ${it.detailRecipeItem.category}"
                            tvTime.text = "Время: ${it.detailRecipeItem.time}"
                            tvCountPortions.text = "Кол-во порций: ${it.detailRecipeItem.countPortion}"
                            progressBar.visibility = View.GONE
                            clContent.visibility = View.VISIBLE
                            clError.visibility = View.GONE
                            binding.containerViewPager.isEnabled = true
                            if (it.detailRecipeItem.kkal.isNotEmpty()){
                                tvKkal.text = it.detailRecipeItem.kkal
                                tvFats.text = it.detailRecipeItem.fats + "г"
                                tvProteins.text = it.detailRecipeItem.proteins + "г"
                                tvCarbs.text = it.detailRecipeItem.carbohydrates + "г"
                            }
                        }
                        is DetailRecipeState.ErrorGetDetailRecipe -> {
                            btnRetryLoad.isEnabled = true
                            tvToolbarName.text = "Ошибка"
                            progressBar.visibility = View.GONE
                            clContent.visibility = View.GONE
                            clError.visibility = View.VISIBLE
                        }
                        is DetailRecipeState.RecipeItem -> {
                            if (it.recipe.isFavorite){
                                btnFavorite.setBackgroundResource(R.drawable.icon_favorite_enable)
                            } else {
                                btnFavorite.setBackgroundResource(R.drawable.icon_favorite_disable)
                            }
                        }
                        is DetailRecipeState.ReplaceIconFavorite -> {
                            if (it.isFavorite){
                                btnFavorite.setBackgroundResource(R.drawable.icon_favorite_enable)
                            } else {
                                btnFavorite.setBackgroundResource(R.drawable.icon_favorite_disable)
                            }
                        } else -> {}
                    }
                }
            }
        }
    }

    private fun setViews(){
        binding.containerViewPager.isEnabled = false
        setListenersInView()
    }

    private fun setListenersInView(){
        with(binding){
            btnRetryLoad.setOnClickListener {
                btnRetryLoad.isEnabled = false
                progressBar.visibility = View.VISIBLE
                intent.getStringExtra(EXTRA_URL)?.let { url ->
                    viewModel.getDetailRecipe(url)
                }
            }
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnFavorite.setOnClickListener {
                if (recipeId != UNDEFINED_ID) viewModel.updateFavoriteRecipe(recipeId)
            }
        }
    }

    private fun setupViewPager(detailRecipe: DetailRecipeModel){
        val ingrFragment = IngredientsFragment.getInstance(detailRecipe)
        val stepFragment = StepRecipeFragment.getInstance(detailRecipe)
        val listFragment = listOf(ingrFragment, stepFragment)
        pageAdapter = DetailRecipePageAdapter(listFragment, supportFragmentManager, lifecycle)
        with(binding){
            containerViewPager.adapter = pageAdapter
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        containerViewPager.currentItem = tab.position
                    }
                    if (tab?.position == 0 && scrollView.scrollY > tabLayout.top){
                        scrollView.smoothScrollTo(0, tabLayout.top, 1000)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }
        binding.containerViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    with(binding){
                        tabLayout.selectTab(tabLayout.getTabAt(position))
                    }
                }
            })
    }

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_PREVIEW = "extra_preview"
        const val EXTRA_ID = "extra_id"

        private const val UNDEFINED_URL = "undefined_url"
        private const val UNDEFINED_PREVIEW = "undefined_preview"
        private const val UNDEFINED_ID = -1
    }

}