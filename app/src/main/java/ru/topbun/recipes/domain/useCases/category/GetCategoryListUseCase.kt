package ru.topbun.recipes.domain.useCases.category

import ru.topbun.recipes.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke() = repository.getListCategory()

}