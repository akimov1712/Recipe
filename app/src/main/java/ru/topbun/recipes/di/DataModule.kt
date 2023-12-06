package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.data.sources.database.AppDatabase
import ru.topbun.recipes.data.sources.database.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object{

        @Provides
        @Singleton
        fun provideDao(application: Application): RecipeDao {
            return AppDatabase.getInstance(application).dao()
        }

    }

}