package ru.topbun.recipes.domain.entity.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RecipeEntity(
    val id: Int,
    val name: String,
    val category: String,
    val preview: String,
    val time: String,
    val countIngredients: String,
    val countPortions: String,
    val urlFullRecipe: String,
    val isFavorite: Boolean
)