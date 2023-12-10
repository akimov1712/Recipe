package ru.topbun.recipes.data.sources.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

@Entity(tableName = "recipes")
data class RecipeDbEntity(
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
){
    fun toEntity() = RecipeEntity(
        id = id,
        name = name,
        category = category,
        preview = preview,
        time = time,
        countIngredients = countIngredients,
        countPortions = countPortions,
        urlFullRecipe = urlFullRecipe,
        isFavorite = isFavorite
    )

    companion object{
        fun fromEntity(entity: RecipeEntity) = RecipeDbEntity(
            id = entity.id,
            name = entity.name,
            category = entity.category,
            preview = entity.preview,
            time = entity.time,
            countIngredients = entity.countIngredients,
            countPortions = entity.countPortions,
            urlFullRecipe = entity.urlFullRecipe,
            isFavorite = entity.isFavorite
        )
    }
}

