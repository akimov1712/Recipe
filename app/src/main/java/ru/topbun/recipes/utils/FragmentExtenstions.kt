package ru.topbun.recipes.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.topbun.recipes.R

fun Fragment.findTopNavController(): NavController{
    val topLevelHost = requireActivity().supportFragmentManager
        .findFragmentById(R.id.fragment_container) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}