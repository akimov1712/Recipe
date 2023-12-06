package ru.topbun.recipes.presentation.detail

import ru.topbun.recipes.domain.entity.DetailRecipeEntity
import ru.topbun.recipes.domain.entity.RecipeEntity

sealed class DetailRecipeState{
    data object ErrorGetDetailRecipe: DetailRecipeState()
    class ReplaceIconFavorite(val isFavorite: Boolean): DetailRecipeState()
    class DetailRecipe(val detailRecipeItem: DetailRecipeEntity): DetailRecipeState()
    class RecipeItem(val recipe: RecipeEntity): DetailRecipeState()
    data object Loading: DetailRecipeState()
}