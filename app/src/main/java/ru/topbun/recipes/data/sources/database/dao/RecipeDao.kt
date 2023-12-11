package ru.topbun.recipes.data.sources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.data.sources.database.entity.RecipeDbEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeDbEntity)

    @Query("SELECT * FROM recipes WHERE category =:category ORDER BY CASE WHEN time = '' AND preview = '' THEN 1 WHEN time = '' THEN 2 WHEN preview = '' THEN 3 ELSE 0 END")
    fun getListRecipesForCategory(category: String): Flow<List<RecipeDbEntity>>

    @Query("SELECT * FROM recipes WHERE isFavorite = '1'")
    fun getListFavoriteRecipes(): Flow<List<RecipeDbEntity>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' ORDER BY " +
            "  CASE " +
            "    WHEN time = '' AND preview = '' THEN 1 " +
            "    WHEN time = '' THEN 2 " +
            "    WHEN preview = '' THEN 3 " +
            "    ELSE 0 " +
            "  END " +
            "")
    fun getRecipe(query: String): Flow<List<RecipeDbEntity>>

    @Query("SELECT * FROM recipes WHERE id=:id LIMIT 1")
    suspend fun getRecipeForId(id: Int): RecipeDbEntity


}