package ru.topbun.recipes.presentation.main.favorite

import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.presentation.main.search.SearchState

sealed class FavoriteState{

    data object ErrorRecipe: FavoriteState()
    class RecipeList(val recipeList: List<RecipeModel>): FavoriteState()
    data object Loading: FavoriteState()

}
