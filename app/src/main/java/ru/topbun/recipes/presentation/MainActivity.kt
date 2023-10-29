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

    private fun setViews(){
        navigationBottomMenu()
    }


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