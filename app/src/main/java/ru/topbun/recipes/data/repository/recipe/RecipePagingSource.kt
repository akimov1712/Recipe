package ru.topbun.recipes.data.repository.recipe

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.topbun.recipes.data.sources.database.dao.RecipeDao
import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

class RecipePagingSource (
    private val dao: RecipeDao,
    private val pageSize: Int
): PagingSource<Int, RecipeEntity>() {

    override fun getRefreshKey(state: PagingState<Int, RecipeEntity>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeEntity> {
        TODO("Not yet implemented")
    }

}