package ru.topbun.recipes.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val category: String,
    val preview: String,
    val time: String,
    val countIngredients: String,
    val countPortions: String,
    val urlFullRecipe: String,
    val isFavorite: Boolean = false
)