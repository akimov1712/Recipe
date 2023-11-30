package ru.topbun.recipes.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.jsoup.Jsoup
import ru.topbun.recipes.data.database.RecipeDao
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.repository.RecipeRepository
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {

    override fun getListFavoriteRecipe() = recipeDao.getListFavoriteRecipes()
    override fun getRecipe(query: String) = recipeDao.getRecipe(query)
    override fun getRecipeListForCategory(category: String) = recipeDao.getListRecipesForCategory(category)
    override suspend fun getRecipeForId(id: Int) = recipeDao.getRecipeForId(id)
    override suspend fun addRecipe(recipe: RecipeModel) { recipeDao.addRecipe(recipe) }

    override suspend fun getDetailRecipe(url: String): DetailRecipeModel? {
        return try {
            val result = CoroutineScope(Dispatchers.IO).async {
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

}