package ru.topbun.recipes.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.domain.entity.category.CategoryEntity

interface CategoryRepository {

    fun getListCategory(): Flow<List<CategoryEntity>>

}