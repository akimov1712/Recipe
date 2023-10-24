package ru.topbun.recipes.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.recipes.domain.entity.DetailRecipeModel
import ru.topbun.recipes.domain.entity.RecipeModel

@Database(entities = [
    RecipeModel::class,
    DetailRecipeModel::class
], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dao(): RecipeDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "test.db"

        fun getInstance(application: Application) = INSTANCE ?: synchronized(this){
            INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()

    }
}