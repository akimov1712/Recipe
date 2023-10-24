package ru.topbun.recipes.presentation

import ru.topbun.recipes.domain.entity.RecipeModel

sealed class State{
    class RecipeList(val recipeList: List<RecipeModel>): State()
}
