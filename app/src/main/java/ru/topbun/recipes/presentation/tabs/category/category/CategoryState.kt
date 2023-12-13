package ru.topbun.recipes.presentation.tabs.category.category

import ru.topbun.recipes.domain.entity.category.CategoryEntity
import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

sealed class CategoryState{

    class CategoryList(val categoryList: List<CategoryEntity>): CategoryState()
    data object Loading: CategoryState()

}
