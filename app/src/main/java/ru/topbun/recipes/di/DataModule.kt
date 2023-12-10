package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.data.sources.database.AppDatabase
import ru.topbun.recipes.data.sources.database.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.topbun.recipes.data.sources.database.dao.CategoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object{

        @Provides
        @Singleton
        fun provideRecipeDao(application: Application): RecipeDao {
            return AppDatabase.getInstance(application).recipeDao()
        }

        @Provides
        @Singleton
        fun provideCategoryDao(application: Application): CategoryDao {
            return AppDatabase.getInstance(application).categoryDao()
        }

    }

}