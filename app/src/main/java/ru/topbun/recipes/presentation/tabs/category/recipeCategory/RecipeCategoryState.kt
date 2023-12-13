package ru.topbun.recipes.presentation.tabs.category.recipeCategory

import ru.topbun.recipes.domain.entity.category.CategoryEntity
import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

sealed class RecipeCategoryState{

    class RecipeList(val recipeList: List<RecipeEntity>): RecipeCategoryState()
    data object Loading: RecipeCategoryState()
    data object RecipeError: RecipeCategoryState()

}
