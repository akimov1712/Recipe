package ru.topbun.recipes.data.sources.database

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.topbun.recipes.domain.entity.RecipeEntity

@Database(entities = [
    RecipeDbEntity::class,
], version = 9, exportSchema = true,
    autoMigrations = [
        AutoMigration(8,9),
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dao(): RecipeDao

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