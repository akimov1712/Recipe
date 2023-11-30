package ru.topbun.recipes.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.domain.entity.RecipeModel

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeModel)

    @Query("SELECT * FROM recipes WHERE category =:category ORDER BY CASE WHEN time = '' AND preview = '' THEN 1 WHEN time = '' THEN 2 WHEN preview = '' THEN 3 ELSE 0 END")
    fun getListRecipesForCategory(category: String): Flow<List<RecipeModel>>

    @Query("SELECT * FROM recipes WHERE isFavorite = '1'")
    fun getListFavoriteRecipes(): Flow<List<RecipeModel>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' ORDER BY CASE WHEN time = '' AND preview = '' THEN 1 WHEN time = '' THEN 2 WHEN preview = '' THEN 3 ELSE 0 END")
    fun getRecipe(query: String): Flow<List<RecipeModel>>

    @Query("SELECT * FROM recipes WHERE id=:id LIMIT 1")
    suspend fun getRecipeForId(id: Int): RecipeModel


}