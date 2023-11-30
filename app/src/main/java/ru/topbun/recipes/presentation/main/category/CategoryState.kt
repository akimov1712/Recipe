package ru.topbun.recipes.presentation.main.category

import ru.topbun.recipes.domain.entity.RecipeModel

sealed class CategoryState{

    class RecipeList(val recipeList: List<RecipeModel>): CategoryState()
    data object Loading: CategoryState()

}
