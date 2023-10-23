package ru.topbun.recipes.data.repository

import android.app.Application
import android.content.Context
import ru.topbun.recipes.data.database.RecipeDao
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.entity.RecipeResponseModel
import ru.topbun.recipes.domain.repository.RecipeRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val application: Application,
    private val recipeDao: RecipeDao
): RecipeRepository {

    override fun getListRecipe() = recipeDao.getListRecipes()

    override suspend fun getRecipe(url: String): DetailRecipeModel {
        TODO("Not yet implemented")
    }

    override suspend fun addRecipe(recipe: RecipeModel) {
        recipeDao.addRecipe(recipe)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch{
            val countRecipes = recipeDao.getCountRecipesInDb()
            if (countRecipes == 0){
                initRecipesFromJSON()
            }
        }
    }

    private suspend fun initRecipesFromJSON(){
        val stringJson = loadJSONFromAsset(application, "recipes.json")
        val recipeList = Gson().fromJson(stringJson, RecipeResponseModel::class.java)
        recipeList.forEach {
            addRecipe(it)
        }
    }

    private fun loadJSONFromAsset(context: Context, filename: String): String {
        return try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            ""
        }
    }

}