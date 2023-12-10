package ru.topbun.recipes.presentation.main.search

import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

sealed class SearchState{

    data object ErrorRecipe: SearchState()
    class RecipeList(val recipeList: List<RecipeEntity>): SearchState()
    data object Loading: SearchState()

}
