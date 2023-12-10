package ru.topbun.recipes.data.sources.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.topbun.recipes.domain.entity.category.CategoryEntity

@Entity("category")
data class CategoryDbEntity(
    @PrimaryKey
    val name: String,
    val imageLink: String
) {
    fun toEntity() = CategoryEntity(
        name = name,
        imageLink = imageLink
    )
}
