package ru.topbun.recipes.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jsoup.Jsoup
import ru.topbun.recipes.data.sources.database.RecipeDao
import ru.topbun.recipes.data.sources.database.entity.RecipeDbEntity
import ru.topbun.recipes.domain.ConnectException
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.entity.DetailRecipeEntity
import ru.topbun.recipes.domain.entity.IngrEntity
import ru.topbun.recipes.domain.entity.RecipeEntity
import ru.topbun.recipes.domain.entity.StepRecipeEntity
import ru.topbun.recipes.domain.repository.RecipeRepository
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {

    override fun getListFavoriteRecipe(): Flow<List<RecipeEntity>> {
         return recipeDao.getListFavoriteRecipes().map{ list ->
             checkEmptyOrMapListToListEntity(list)
         }
    }

    override fun getRecipe(query: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipe(query).map{ list ->
            checkEmptyOrMapListToListEntity(list)
        }
    }

    override fun getRecipeListForCategory(category: String): Flow<List<RecipeEntity>> {
        return recipeDao.getListRecipesForCategory(category).map{ list ->
            checkEmptyOrMapListToListEntity(list)
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
                val ingrList = mutableListOf<IngrEntity>()
                val stepRecipeList = mutableListOf<StepRecipeEntity>()

                val doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .get()

                val name = doc.select(CSS_NAME).text()
                val category = doc.select(CSS_CATEGORY)[1].select(CSS_CATEGORY_ITEM).attr("content")
                val time = doc.select(CSS_TIME).text()
                val countPortion = doc.select(CSS_COUNT_PORTION).text()
                val kkal = doc.select(CSS_KKAL).text()
                val fats = doc.select(CSS_FATS).text()
                val proteins = doc.select(CSS_PROTEINS).text()
                val carb = doc.select(CSS_CARB).text()

                doc.select(CSS_INGR).forEach {
                    val name = it.select(CSS_INGR_NAME).text()
                    var count = it.select(CSS_INGR_COUNT).text()
                    if (count.isNullOrBlank()) count = "По вкусу"
                    ingrList.add(IngrEntity(name, count))
                }

                doc.select(CSS_STEP).forEach {
                    val descr = it.select(CSS_STEP_DESCR).text()
                    val image = it.select(CSS_STEP_IMAGE).attr("src")
                    stepRecipeList.add(StepRecipeEntity(descr, image))
                }

                DetailRecipeEntity(name, category, time, countPortion,
                    kkal, fats, proteins, carb, ingrList, stepRecipeList)
            }

            return result.await()
        } catch (e: IOException) {
            throw ConnectException()
        }
    }

    private fun checkEmptyOrMapListToListEntity(list: List<RecipeDbEntity>): List<RecipeEntity> {
        if (list.isEmpty()) {
            throw NotFoundRecipesException()
        }
        return list.map { it.toEntity() }
    }

    companion object{

        private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"

        private const val CSS_NAME = ".emotion-gl52ge"
        private const val CSS_CATEGORY = "li[itemprop=\"itemListElement\"]"
        private const val CSS_CATEGORY_ITEM = "meta[itemprop=\"name\"]"
        private const val CSS_TIME = ".emotion-my9yfq"
        private const val CSS_COUNT_PORTION = ".emotion-1047m5l"
        private const val CSS_KKAL = "span[itemprop=\"calories\"]"
        private const val CSS_FATS = "span[itemprop=\"fatContent\"]"
        private const val CSS_PROTEINS = "span[itemprop=\"proteinContent\"]"
        private const val CSS_CARB = "span[itemprop=\"carbohydrateContent\"]"

        private const val CSS_INGR = ".emotion-7yevpr"
        private const val CSS_INGR_NAME = "span[itemprop=\"recipeIngredient\"]"
        private const val CSS_INGR_COUNT = ".emotion-bsdd3p"

        private const val CSS_STEP = "div[itemprop=\"recipeInstructions\"]"
        private const val CSS_STEP_DESCR = ".emotion-wdt5in"
        private const val CSS_STEP_IMAGE = ".emotion-0"

    }

}