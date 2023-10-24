package ru.topbun.recipes.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val descr: String,
    val category: String,
    val preview: String,
    val time: String,
    val countPortions: Int,
    val urlFullRecipe: String
)