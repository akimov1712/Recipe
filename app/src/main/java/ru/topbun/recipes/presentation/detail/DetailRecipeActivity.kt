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
        val url = intent.getStringExtra(EXTRA_URL)
        url?.let { viewModel.getDetailRecipe(it) }

        val preview = intent.getStringExtra(EXTRA_PREVIEW)
        Picasso.get().load(preview).error(R.color.white).into(binding.ivPreview)
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
                            if (it.detailRecipeItem.kkal.isNotEmpty()){
                                tvKkal.text = it.detailRecipeItem.kkal
                                tvFats.text = it.detailRecipeItem.fats + "г"
                                tvProteins.text = it.detailRecipeItem.proteins + "г"
                                tvCarbs.text = it.detailRecipeItem.carbohydrates + "г"
                            }
                            progressBar.visibility = View.GONE
                            clContent.visibility = View.VISIBLE
                            clError.visibility = View.GONE
                        }
                        is DetailRecipeState.ErrorGetDetailRecipe -> {
                            supportActionBar?.title = "Ошибка"
                            progressBar.visibility = View.GONE
                            clContent.visibility = View.GONE
                            clError.visibility = View.VISIBLE
                        } else -> {}
                    }
                }
            }
        }
    }

    private fun setViews(){
        setListenersInView()
    }

    private fun setListenersInView(){
        with(binding){
            btnRetryLoad.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                intent.getStringExtra(EXTRA_URL)?.let { url ->
                    viewModel.getDetailRecipe(url)
                }
            }
            btnBack.setOnClickListener {
                onBackPressed()
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
    }

}