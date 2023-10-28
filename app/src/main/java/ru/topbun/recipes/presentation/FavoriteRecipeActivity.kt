package ru.topbun.recipes.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.ActivityFavoriteRecipeBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.MainViewModel
import ru.topbun.recipes.presentation.main.adapter.RecipeAdapter
import javax.inject.Inject

class FavoriteRecipeActivity : AppCompatActivity() {

    private val component by lazy{
        (application as App).component
    }
    private val binding by lazy { ActivityFavoriteRecipeBinding.inflate(layoutInflater) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java] }

    @Inject
    lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViews()
        observeViewModel()
    }

    private fun setViews(){
        setListenersInView()
        setAdapter()
    }

    private fun setAdapter(){
        recipeAdapter.setOnRecipeClickListener = { url, preview ->
            val intent = Intent(this, DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, url)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            startActivity(intent)
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipe.adapter = recipeAdapter
    }



    private fun observeViewModel(){
        with(binding){
            viewModel.recipeFavoriteList.observe(this@FavoriteRecipeActivity) {
                if (it.isEmpty()) {
                    tvNotFound.visibility = View.VISIBLE
                    tvToolbarName.text = "Избранное"
                } else {
                    tvNotFound.visibility = View.GONE
                    tvToolbarName.text = "Избранное " + it.size
                }
                recipeAdapter.submitList(it)
            }
        }
    }

    private fun setListenersInView(){
        with(binding){
            btnBack.setOnClickListener {
                finish()
            }
        }
    }

}