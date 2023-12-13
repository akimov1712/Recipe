package ru.topbun.recipes.presentation.tabs.favorite

import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

sealed class FavoriteState{

    data object ErrorRecipe: FavoriteState()
    class RecipeList(val recipeList: List<RecipeEntity>): FavoriteState()
    data object Loading: FavoriteState()

}
