package ru.topbun.recipes.presentation.main.favorite

import ru.topbun.recipes.domain.entity.RecipeEntity

sealed class FavoriteState{

    data object ErrorRecipe: FavoriteState()
    class RecipeList(val recipeList: List<RecipeEntity>): FavoriteState()
    data object Loading: FavoriteState()

}
