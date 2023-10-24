package ru.topbun.recipes.presentation

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.ActivityMainBinding
import ru.topbun.recipes.presentation.adapter.RecipeAdapter
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val component by lazy{
        (application as App).component
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[ViewModel::class.java] }
    private val recipeAdapter by lazy { RecipeAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViews()
        observeViewModel()
        viewModel.getListRecipe()
        binding.rvRecipes.adapter = recipeAdapter
    }

    private fun setViews(){
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.getRecipeQuery(newText) } ?: run {
                    viewModel.getListRecipe()
                }
                return true
            }
        })
    }

    private fun observeViewModel(){
        viewModel.state.observe(this){
            when(it){
                is State.RecipeList -> {
                    recipeAdapter.submitList(it.recipeList)
                }
                else -> {}
            }
        }
    }

}