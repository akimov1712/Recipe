package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.data.database.AppDatabase
import ru.topbun.recipes.data.database.RecipeDao
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    companion object{

        @Provides
        fun provideDao(application: Application): RecipeDao {
            return AppDatabase.getInstance(application).dao()
        }

    }

}