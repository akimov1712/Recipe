package ru.topbun.recipes.presentation.tabs.category.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.useCases.category.GetCategoryListUseCase
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeListForCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val state get() = _state.asStateFlow()

     private fun getCategoryList() = viewModelScope.launch {
         _state.value = CategoryState.Loading
        getCategoryListUseCase().collect{
            _state.value = CategoryState.CategoryList(it)
        }
    }

    init {
        getCategoryList()
    }


}