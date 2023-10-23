package ru.topbun.recipes.di

import ru.topbun.recipes.data.repository.RecipeRepositoryImpl
import ru.topbun.recipes.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository

}