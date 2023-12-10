package ru.topbun.recipes.data.sources.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.data.sources.database.entity.CategoryDbEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getCategoryList(): Flow<List<CategoryDbEntity>>

}