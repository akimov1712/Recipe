package ru.topbun.recipes.presentation.tabs.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeUseCase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    val state get() = _state.asStateFlow()

    fun getRecipeQuery(query: String) = viewModelScope.launch {
        try {
            getRecipeUseCase(query).collect {
                _state.value = SearchState.RecipeList(it)
            }
        } catch (e: NotFoundRecipesException) {
            _state.value = SearchState.ErrorRecipe
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