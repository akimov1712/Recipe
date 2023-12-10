package ru.topbun.recipes.data.repository

import kotlinx.coroutines.flow.map
import ru.topbun.recipes.data.sources.database.dao.CategoryDao
import ru.topbun.recipes.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
): CategoryRepository {

    override fun getListCategory() = dao.getCategoryList().map{ it.map { item -> item.toEntity() } }

}