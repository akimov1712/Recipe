package ru.topbun.recipes.presentation.main.home

import ru.topbun.recipes.domain.entity.RecipeModel

sealed class HomeState{
    class RecipeList(val recipeList: List<RecipeModel>): HomeState()
    data object Loading: HomeState()
}
