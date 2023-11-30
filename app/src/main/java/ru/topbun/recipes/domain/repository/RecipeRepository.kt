package ru.topbun.recipes.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel

interface RecipeRepository {

    fun getListFavoriteRecipe(): Flow<List<RecipeModel>>
    fun getRecipeListForCategory(category: String): Flow<List<RecipeModel>>
    fun getRecipe(query: String): Flow<List<RecipeModel>>
    suspend fun getRecipeForId(id: Int): RecipeModel
    suspend fun addRecipe(recipe: RecipeModel)
    suspend fun getDetailRecipe(url: String): DetailRecipeModel?

}