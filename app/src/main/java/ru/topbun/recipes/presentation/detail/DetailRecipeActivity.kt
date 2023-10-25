package ru.topbun.recipes.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.ActivityDetailRecipeBinding
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.main.MainViewModel
import javax.inject.Inject

class DetailRecipeActivity : AppCompatActivity() {

    private val component by lazy {(application as App).component}
    private val binding by lazy {ActivityDetailRecipeBinding.inflate(layoutInflater)}

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java] }

    private lateinit var pageAdapter: DetailRecipePageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViews()
        observeViewModel()
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){

            }
        }
    }

    private fun setViews(){
        setListenersInView()
        setupViewPager(DetailRecipeModel(1))
    }

    private fun setListenersInView(){
        with(binding){
            btnBack.setOnClickListener {
                finish()
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
    }

}