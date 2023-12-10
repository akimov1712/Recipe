package ru.topbun.recipes.data.sources.database

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.recipes.data.sources.database.dao.CategoryDao
import ru.topbun.recipes.data.sources.database.dao.RecipeDao
import ru.topbun.recipes.data.sources.database.entity.CategoryDbEntity
import ru.topbun.recipes.data.sources.database.entity.RecipeDbEntity

@Database(entities = [
    RecipeDbEntity::class,
    CategoryDbEntity::class
], version = 9, exportSchema = true,
    autoMigrations = [
        AutoMigration(8,9),
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun categoryDao(): CategoryDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "recipe.db"

        fun getInstance(application: Application) = INSTANCE ?: synchronized(this){
            INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
        }

        private fun buildDatabase(application: Application) =
            Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DB_NAME
            ).createFromAsset("database/recipe.db")
            .build()

    }
}