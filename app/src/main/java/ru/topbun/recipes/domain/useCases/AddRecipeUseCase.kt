package ru.topbun.recipes.domain.useCases

import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(recipe: RecipeModel){
        repository.addRecipe(recipe)
    }

}