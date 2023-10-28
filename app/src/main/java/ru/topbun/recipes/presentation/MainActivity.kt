package ru.topbun.recipes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setViews()
    }

//    private fun getData(){
//        val query = binding.editText.text.toString()
//        viewModel.getRecipeQuery(query)
//    }
//
    private fun setViews(){
        navigationBottomMenu()
//        setListenersInView()
//        setAdapter()
//        setTextWathcherInEditText()
    }
//
//    private fun setAdapter(){
//        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview ->
//            val intent = Intent(this, DetailRecipeActivity::class.java)
//            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
//            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
//            startActivity(intent)
//        }
//        recipeAdapter.setOnFavoriteClickListener = {
//            viewModel.updateFavoriteRecipe(it)
//        }
//        binding.rvRecipes.adapter = recipeAdapter
//    }
//
//    private fun setTextWathcherInEditText(){
//        binding.editText.addTextChangedListener ( object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                s.toString().let { viewModel.getRecipeQuery(it) }
//                if(s?.isNotBlank() == true){
//                    binding.btnClear.visibility = View.VISIBLE
//                } else {
//                    binding.btnClear.visibility = View.GONE
//                    binding.rvRecipes.scrollToPosition(0)
//                }
//            }
//        })
//    }
//
//    private fun observeViewModel(){
//        with(binding){
//            viewModel.state.observe(this@MainActivity){
//                when(it){
//                    is HomeState.RecipeList -> {
//                        if (it.recipeList.isEmpty()) tvNotFount.visibility = View.VISIBLE else tvNotFount.visibility = View.GONE
//                        recipeAdapter.submitList(it.recipeList)
//                    }
//                    else -> {}
//                }
//            }
//            viewModel.recipeList.observe(this@MainActivity){
//                if (it.isEmpty()) tvNotFount.visibility = View.VISIBLE else tvNotFount.visibility = View.GONE
//                recipeAdapter.submitList(it)
//            }
//        }
//    }
//
//    private fun setListenersInView(){
//        with(binding){
//            btnClear.setOnClickListener {
//                editText.text.clear()
//                rvRecipes.scrollToPosition(0)
//            }
//            btnFavoriteRecipes.setOnClickListener {
//                val intent = Intent(this@MainActivity, FavoriteRecipeActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }

    private fun navigationBottomMenu() {
        val navController = findNavController(R.id.fragment_container)
        val setFragments = setOf(
            R.id.homeFragment,
            R.id.categoryFragment,
            R.id.favoriteFragment,
            R.id.categoryFragment,
        )
        val appBarConfigurator = AppBarConfiguration(
            setFragments
        )
        setupActionBarWithNavController(navController, appBarConfigurator)
        binding.bottomMenu.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentBackStackEntry = navController.currentBackStackEntry
            if (destination.id in setFragments && currentBackStackEntry?.destination?.id != destination.id) {
                navController.navigate(destination.id)
            }
        }
    }

}