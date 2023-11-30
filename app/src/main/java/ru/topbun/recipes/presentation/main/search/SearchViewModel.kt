package ru.topbun.recipes.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.presentation.main.home.HomeState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    val state get() = _state.asStateFlow()

    fun getRecipeQuery(query: String) = viewModelScope.launch{
        viewModelScope.launch {
            getRecipeUseCase(query).collect {
                _state.value = SearchState.RecipeList(it)
                if (it.isEmpty()) _state.value = SearchState.ErrorRecipe
            }
        }
    }

    fun updateFavoriteRecipe(id: Int){
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
        }
    }

}