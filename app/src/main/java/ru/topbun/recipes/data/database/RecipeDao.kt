package ru.topbun.recipes.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.topbun.recipes.domain.entity.RecipeModel

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeModel)

    @Query("SELECT * FROM recipes")
    fun getListRecipes(): LiveData<List<RecipeModel>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%'")
    fun getRecipe(query: String): LiveData<List<RecipeModel>>

    @Query("SELECT COUNT(*) FROM recipes LIMIT 2")
    suspend fun getCountRecipesInDb(): Int

}