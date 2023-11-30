package ru.topbun.recipes.presentation.main.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeListForCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val getRecipeListForCategoryUseCase: GetRecipeListForCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val state get() = _state.asStateFlow()

    fun getRecipeByCategory(category: String)= viewModelScope.launch{
        getRecipeListForCategoryUseCase(category).collect {
            _state.emit(CategoryState.RecipeList(it))
        }
    }


    fun updateFavoriteRecipe(id: Int) {
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
        }
    }

}