package ru.topbun.recipes.presentation.main.favorite

import ru.topbun.recipes.domain.entity.RecipeModel

sealed class FavoriteState{

    data object ErrorRecipe: FavoriteState()
    class RecipeList(val recipeList: List<RecipeModel>): FavoriteState()

}
