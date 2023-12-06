package ru.topbun.recipes.presentation.main.category

import ru.topbun.recipes.domain.entity.RecipeEntity

sealed class CategoryState{

    class RecipeList(val recipeList: List<RecipeEntity>): CategoryState()
    data object Loading: CategoryState()
    data object RecipeError: CategoryState()

}
