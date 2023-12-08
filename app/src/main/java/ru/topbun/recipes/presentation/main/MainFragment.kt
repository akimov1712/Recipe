package ru.topbun.recipes.presentation.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentMainBinding
import ru.topbun.recipes.presentation.base.BaseFragment
import ru.topbun.recipes.presentation.base.OnNavigateToDetailRecipe

@AndroidEntryPoint
class MainFragment :
    BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    OnNavigateToDetailRecipe {

    override fun setViews() {
        super.setViews()
        navigationBottomMenu()
    }

    private fun navigationBottomMenu() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomMenu.setupWithNavController(navController)
    }

    override fun navigateToDetailRecipeFragment(id: Int, url: String, preview: String) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailRecipeFragment(id,url, preview)
        )
    }
}