package ru.topbun.recipes.domain.useCases.recipe

import ru.topbun.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetListFavoriteRecipeUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke() = repository.getListFavoriteRecipe()

}