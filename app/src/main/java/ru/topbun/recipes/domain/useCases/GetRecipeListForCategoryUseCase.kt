package ru.topbun.recipes.domain.useCases

import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeListForCategoryUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke(category: String) = repository.getRecipeListForCategory(category)

}