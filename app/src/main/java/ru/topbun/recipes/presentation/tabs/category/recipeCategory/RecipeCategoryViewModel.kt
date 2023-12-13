package ru.topbun.recipes.presentation.tabs.category.recipeCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeListForCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class RecipeCategoryViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val getRecipeListForCategoryUseCase: GetRecipeListForCategoryUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<RecipeCategoryState>(RecipeCategoryState.Loading)
    val state get() = _state.asStateFlow()

    fun getRecipeByCategory(category: String) = viewModelScope.launch{
        _state.value = RecipeCategoryState.Loading
        try {
            getRecipeListForCategoryUseCase(category).collect {
                _state.emit(RecipeCategoryState.RecipeList(it))
            }
        }catch (e: NotFoundRecipesException){
            _state.value = RecipeCategoryState.RecipeError
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