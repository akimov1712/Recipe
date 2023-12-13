package ru.topbun.recipes.presentation.tabs

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentTabsBinding
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class TabsFragment :BaseFragment<FragmentTabsBinding>(FragmentTabsBinding::inflate){

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
}