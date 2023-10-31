package ru.topbun.recipes.presentation.detail

import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel

sealed class DetailRecipeState{
    data object ErrorGetDetailRecipe: DetailRecipeState()
    class ReplaceIconFavorite(val isFavorite: Boolean): DetailRecipeState()
    class DetailRecipe(val detailRecipeItem: DetailRecipeModel): DetailRecipeState()
    class RecipeItem(val recipe: RecipeModel): DetailRecipeState()
}