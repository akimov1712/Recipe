package ru.topbun.recipes.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.topbun.recipes.domain.entity.DetailRecipeEntity
import ru.topbun.recipes.domain.entity.RecipeEntity

interface RecipeRepository {

    fun getListFavoriteRecipe(): Flow<List<RecipeEntity>>
    fun getRecipeListForCategory(category: String): Flow<List<RecipeEntity>>
    fun getRecipe(query: String): Flow<List<RecipeEntity>>
    suspend fun getRecipeForId(id: Int): RecipeEntity
    suspend fun addRecipe(recipe: RecipeEntity)
    suspend fun getDetailRecipe(url: String): DetailRecipeEntity

}