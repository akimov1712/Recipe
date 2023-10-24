package ru.topbun.recipes.domain.repository

import androidx.lifecycle.LiveData
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel

interface RecipeRepository {

    fun getListRecipe(): LiveData<List<RecipeModel>>
    fun getRecipe(query: String): LiveData<List<RecipeModel>>
    suspend fun addRecipe(recipe: RecipeModel)

}