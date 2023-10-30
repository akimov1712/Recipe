package ru.topbun.recipes.domain.repository

import androidx.lifecycle.LiveData
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel

interface RecipeRepository {

    fun getListFavoriteRecipe(): LiveData<List<RecipeModel>>
    fun getRecipeListForCategory(category: String): LiveData<List<RecipeModel>>
    fun getRecipe(query: String): LiveData<List<RecipeModel>>
    suspend fun initRecipes()
    suspend fun getRecipeForId(id: Int): RecipeModel
    suspend fun addRecipe(recipe: RecipeModel)
    suspend fun getDetailRecipe(url: String): DetailRecipeModel?

}