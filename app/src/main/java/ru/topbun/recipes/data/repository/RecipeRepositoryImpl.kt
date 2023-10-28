package ru.topbun.recipes.data.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import ru.topbun.recipes.data.database.RecipeDao
import ru.topbun.recipes.domain.entity.RecipeResponseModel
import ru.topbun.recipes.domain.repository.RecipeRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val application: Application,
    private val recipeDao: RecipeDao
): RecipeRepository {

    override fun getListFavoriteRecipe() = recipeDao.getListFavoriteRecipes()

    override fun getRecipe(query: String) = recipeDao.getRecipe(query)

    override suspend fun initRecipes() {
        val countRecipes = recipeDao.getCountRecipesInDb()
        if (countRecipes < 9000){
            recipeDao.deleteRecipes()
            initRecipesFromJSON()
        }
        val sharedPreferences = application.applicationContext.getSharedPreferences("endInit", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("initComplete", true)
        editor.apply()
    }

    override suspend fun getRecipeForId(id: Int) = recipeDao.getRecipeForId(id)

    override suspend fun addRecipe(recipe: RecipeModel) {
        recipeDao.addRecipe(recipe)
    }

    override suspend fun getDetailRecipe(url: String): DetailRecipeModel? {
        return try {
            val result: Deferred<DetailRecipeModel?> = CoroutineScope(Dispatchers.IO).async {
                val ingrList = mutableListOf<Pair<String, String>>()
                val stepRecipeList = mutableListOf<Pair<String, String>>()

                val doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
                    .get()

                val name = doc.select(".emotion-gl52ge").text()
                val category = doc.select("li[itemprop=\"itemListElement\"]")[1].select("meta[itemprop=\"name\"]").attr("content")
                val time = doc.select(".emotion-my9yfq").text()
                val countPortion = doc.select(".emotion-1047m5l").text()
                val kkal = doc.select("span[itemprop=\"calories\"]").text()
                val fats = doc.select("span[itemprop=\"fatContent\"]").text()
                val proteins = doc.select("span[itemprop=\"proteinContent\"]").text()
                val carb = doc.select("span[itemprop=\"carbohydrateContent\"]").text()

                doc.select(".emotion-7yevpr").forEach {
                    val name = it.select("span[itemprop=\"recipeIngredient\"]").text()
                    var count = it.select(".emotion-bsdd3p").text()
                    if (count.isNullOrBlank()) count = "По вкусу"
                    ingrList.add(Pair(name, count))
                }

                doc.select("div[itemprop=\"recipeInstructions\"]").forEach {
                    val descr = it.select(".emotion-wdt5in").text()
                    val image = it.select(".emotion-ducv57").attr("src")
                    stepRecipeList.add(Pair(descr, image))
                }

                DetailRecipeModel(name, category, time, countPortion, kkal, fats, proteins, carb, ingrList, stepRecipeList)
            }

            return result.await()
        } catch (e: IOException) {
            null
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