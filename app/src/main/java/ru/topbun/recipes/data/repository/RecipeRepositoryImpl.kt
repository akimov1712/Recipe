package ru.topbun.recipes.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.jsoup.Jsoup
import ru.topbun.recipes.data.sources.database.RecipeDao
import ru.topbun.recipes.data.sources.database.RecipeDbEntity
import ru.topbun.recipes.domain.ConnectException
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.entity.DetailRecipeEntity
import ru.topbun.recipes.domain.entity.RecipeEntity
import ru.topbun.recipes.domain.repository.RecipeRepository
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {

    override fun getListFavoriteRecipe(): Flow<List<RecipeEntity>> {
         return recipeDao.getListFavoriteRecipes().map{ list ->
             checkEmptyOrMapListToEntity(list)
         }
    }

    override fun getRecipe(query: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipe(query).map{ list ->
            checkEmptyOrMapListToEntity(list)
        }
    }

    override fun getRecipeListForCategory(category: String): Flow<List<RecipeEntity>> {
        return recipeDao.getListRecipesForCategory(category).map{ list ->
            checkEmptyOrMapListToEntity(list)
        }
    }

    override suspend fun getRecipeForId(id: Int): RecipeEntity {
        return recipeDao.getRecipeForId(id).toEntity()
    }

    override suspend fun addRecipe(recipe: RecipeEntity) {
        recipeDao.addRecipe(RecipeDbEntity.fromEntity(recipe))
    }

    override suspend fun getDetailRecipe(url: String): DetailRecipeEntity {
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

                DetailRecipeEntity(name, category, time, countPortion, kkal, fats, proteins, carb, ingrList, stepRecipeList)
            }

            return result.await()
        } catch (e: IOException) {
            throw ConnectException()
        }
    }

    private fun checkEmptyOrMapListToEntity(list: List<RecipeDbEntity>): List<RecipeEntity> {
        if (list.isEmpty()) throw NotFoundRecipesException()
        return list.map { it.toEntity() }
    }

}