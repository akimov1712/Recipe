package ru.topbun.recipes.presentation.detailRecipe

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentDetailRecipeBinding
import ru.topbun.recipes.domain.entity.recipe.DetailRecipeEntity
import ru.topbun.recipes.domain.entity.recipe.IngrListTuple
import ru.topbun.recipes.domain.entity.recipe.StepRecipeListTuple
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class DetailRecipeFragment : BaseFragment<FragmentDetailRecipeBinding>(FragmentDetailRecipeBinding::inflate) {

    private val args by navArgs<DetailRecipeFragmentArgs>()
    private val viewModel by viewModels<DetailRecipeViewModel>()

    private lateinit var pageAdapter: DetailRecipePageAdapter

    override fun setViews() {
        super.setViews()
        binding.containerViewPager.isEnabled = false
        getData()
        setListenersInView()
    }

    private fun getData() {
        viewModel.getRecipe(args.id)
        viewModel.getDetailRecipe(args.url)
        Picasso.get().load(args.preview).into(binding.ivPreview)
    }

     override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding) {
                    with(viewModel) {
                        state.collect {
                            when (it) {
                                is DetailRecipeState.DetailRecipe -> {
                                    progressBar.visibility = View.GONE
                                    setupViewPager(it.detailRecipeItem)
                                    tvName.text = it.detailRecipeItem.name
                                    tvToolbarName.text = it.detailRecipeItem.name
                                    tvCategory.text = "Категория: ${it.detailRecipeItem.category}"
                                    tvTime.text = "Время: ${it.detailRecipeItem.time}"
                                    tvCountPortions.text =
                                        "Кол-во порций: ${it.detailRecipeItem.countPortion}"
                                    progressBar.visibility = View.GONE
                                    clContent.visibility = View.VISIBLE
                                    clError.visibility = View.GONE
                                    binding.containerViewPager.isEnabled = true
                                    if (it.detailRecipeItem.kkal.isNotEmpty()) {
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
                                    val name = it.recipe.name
                                    tvName.text = name
                                    tvToolbarName.text = name
                                    if (it.recipe.isFavorite) {
                                        btnFavorite.setBackgroundResource(R.drawable.icon_favorite_enable)
                                    } else {
                                        btnFavorite.setBackgroundResource(R.drawable.icon_favorite_disable)
                                    }
                                }


                                is DetailRecipeState.ReplaceIconFavorite -> {
                                    if (it.isFavorite) {
                                        btnFavorite.setBackgroundResource(R.drawable.icon_favorite_enable)
                                    } else {
                                        btnFavorite.setBackgroundResource(R.drawable.icon_favorite_disable)
                                    }
                                }

                                is DetailRecipeState.Loading -> {
                                    progressBar.visibility = View.VISIBLE
                                    clError.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setListenersInView() {
        with(binding) {
            btnRetryLoad.setOnClickListener {
                btnRetryLoad.isEnabled = false
                viewModel.getDetailRecipe(args.url)
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnFavorite.setOnClickListener {
                viewModel.updateFavoriteRecipe(args.id)
            }
        }
    }

    private fun setupViewPager(detailRecipe: DetailRecipeEntity) {
        val ingrFragment = IngredientsFragment.getInstance(IngrListTuple(detailRecipe.ingrList))
        val stepFragment = StepRecipeFragment.getInstance(StepRecipeListTuple(detailRecipe.stepRecipeList))
        val listFragment = listOf(ingrFragment, stepFragment)
        pageAdapter = DetailRecipePageAdapter(listFragment, childFragmentManager, lifecycle)
        with(binding) {
            containerViewPager.adapter = pageAdapter
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        containerViewPager.currentItem = tab.position
                    }
                    if (tab?.position == 0 && scrollView.scrollY > tabLayout.top) {
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
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    with(binding) {
                        tabLayout.selectTab(tabLayout.getTabAt(position))
                    }
                }
            }
        )
    }
}