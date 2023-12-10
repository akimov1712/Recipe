package ru.topbun.recipes.domain.useCases.recipe

import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetDetailRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(url: String) = repository.getDetailRecipe(url)

}