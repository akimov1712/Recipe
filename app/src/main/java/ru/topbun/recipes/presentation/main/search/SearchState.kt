package ru.topbun.recipes.presentation.main.search

import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.presentation.main.home.HomeState

sealed class SearchState{

    data object ErrorRecipe: SearchState()
    class RecipeList(val recipeList: List<RecipeModel>): SearchState()
    data object Loading: SearchState()

}
