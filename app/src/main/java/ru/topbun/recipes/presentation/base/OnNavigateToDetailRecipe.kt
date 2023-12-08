package ru.topbun.recipes.presentation.base

interface OnNavigateToDetailRecipe {

    fun navigateToDetailRecipeFragment(id: Int, url: String, preview: String)

}