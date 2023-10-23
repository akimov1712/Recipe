package ru.topbun.recipes.domain.useCases

import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(url: String) = repository.getRecipe(url)

}