package ru.topbun.recipes.presentation.main

import ru.topbun.recipes.domain.entity.RecipeModel

sealed class MainState{
    class RecipeList(val recipeList: List<RecipeModel>): MainState()
}
