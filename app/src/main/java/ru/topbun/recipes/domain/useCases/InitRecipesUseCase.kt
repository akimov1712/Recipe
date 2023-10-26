package ru.topbun.recipes.domain.useCases

import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class InitRecipesUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(){
       repository.initRecipes()
    }

}