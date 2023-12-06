package ru.topbun.recipes.presentation.main.home

import ru.topbun.recipes.domain.entity.RecipeEntity

sealed class HomeState{
    class RecipeList(val recipeList: List<RecipeEntity>): HomeState()
    data object Loading: HomeState()
    data class ErrorRecipe(val message: String): HomeState()
}
