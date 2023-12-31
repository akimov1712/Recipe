package ru.topbun.recipes.domain.useCases.recipe

import ru.topbun.recipes.domain.entity.recipe.RecipeEntity
import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(recipe: RecipeEntity){
        repository.addRecipe(recipe)
    }

}