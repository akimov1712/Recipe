package ru.topbun.recipes.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val preview: String,
    val descr: String,
    val time: String,
    val category: String,
    val countPortion: String,
    val isFavorite: Boolean = false,
    val urlFullRecipe: String
)