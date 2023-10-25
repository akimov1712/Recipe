package ru.topbun.recipes.presentation.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.ActivityMainBinding
import ru.topbun.recipes.presentation.main.adapter.RecipeAdapter
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val component by lazy{
        (application as App).component
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
        setTextWathcherInEditText()
    }

    private fun setAdapter(){
        recipeAdapter.setOnRecipeClickListener = {
            val intent = Intent(this, DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, it)
            startActivity(intent)
        }
        binding.rvRecipes.adapter = recipeAdapter
    }

    private fun setTextWathcherInEditText(){
        binding.editText.addTextChangedListener ( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s.toString().let { viewModel.getRecipeQuery(it) }
                if(s?.isNotBlank() == true){
                    binding.btnClear.visibility = View.VISIBLE
                } else {
                    binding.btnClear.visibility = View.GONE
                }
            }
        })
    }

    private fun observeViewModel(){
        with(binding){
            viewModel.state.observe(this@MainActivity){
                when(it){
                    is MainState.RecipeList -> {
                        if (it.recipeList.isEmpty()) tvNotFount.visibility = View.VISIBLE else tvNotFount.visibility = View.GONE
                        recipeAdapter.submitList(it.recipeList)
                        rvRecipes.scrollToPosition(0)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setListenersInView(){
        with(binding){
            btnClear.setOnClickListener {
                editText.text.clear()
                rvRecipes.scrollToPosition(0)
            }
        }
    }

}