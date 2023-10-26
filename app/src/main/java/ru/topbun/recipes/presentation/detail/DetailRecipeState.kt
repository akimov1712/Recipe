package ru.topbun.recipes.presentation.detail

import ru.topbun.recipes.domain.entity.DetailRecipeModel

sealed class DetailRecipeState{
    data object ErrorGetDetailRecipe: DetailRecipeState()
    class DetailRecipe(val detailRecipeItem: DetailRecipeModel): DetailRecipeState()
}