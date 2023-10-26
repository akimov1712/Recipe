package ru.topbun.recipes.domain.useCases

import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeForIdUseCase @Inject constructor(private val repository: RecipeRepository) {

    suspend operator fun invoke(id: Int) = repository.getRecipeForId(id)

}