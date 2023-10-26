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

    override fun getListRecipe() = recipeDao.getListRecipes()
    override fun getListFavoriteRecipe() = recipeDao.getListFavoriteRecipes()

    override fun getRecipe(query: String) = recipeDao.getRecipe(query)

    override suspend fun initRecipes() {
        val countRecipes = recipeDao.getCountRecipesInDb()
        if (countRecipes == 0){
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

                val name = doc.select(".entry-title").text()
                val category = doc.select(".entry-category a").text()

                val preview = doc.select(".entry-image.entry-image--big").attr("style")
                val pattern = Regex("url\\(([^)]+)\\)")
                val matchResult = pattern.find(preview)
                val imageUrl = matchResult?.groupValues?.get(1) ?: ""

                val time = doc.select("span[itemprop=\"totalTime\"]").text()
                val countPortion = doc.select("input[class=\"js-ingredients-serves\"]").attr("value")
                val kkal = doc.select("span[itemprop=\"calories\"] .strong").text()
                val fats = doc.select("span[itemprop=\"fatContent\"] .strong").text()
                val proteins = doc.select("span[itemprop=\"proteinContent\"] .strong").text()
                val carb = doc.select("span[itemprop=\"carbohydrateContent\"] .strong").text()

                doc.select("li[itemprop=\"recipeIngredient\"]").forEach {
                    val name = it.select(".ingredients__name").text()
                    var count = it.select(".ingredients__count").text()
                    if (count.isNullOrBlank()) count = "По вкусу"
                    ingrList.add(Pair(name, count))
                }

                doc.select("li[itemprop=\"recipeInstructions\"]").forEach {
                    val descr = it.select(".recipe-steps__text").text()
                    val image = it.select(".recipe-steps__photo a").attr("href")
                    stepRecipeList.add(Pair(descr, image))
                }

                DetailRecipeModel(name, category, imageUrl, time, countPortion, kkal, fats, proteins, carb, ingrList, stepRecipeList)
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