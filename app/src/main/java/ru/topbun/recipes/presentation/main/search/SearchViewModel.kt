package ru.topbun.recipes.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
): ViewModel() {
    private var oldQuery = ""

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState>
        get() = _state

    fun getRecipeQuery(query: String){
        viewModelScope.launch {
            val recipeList = getRecipeUseCase(query)
            _state.value = SearchState.RecipeList(recipeList)
            if (recipeList.isEmpty()) _state.value = SearchState.ErrorRecipe
            oldQuery = query
        }
    }

    fun updateFavoriteRecipe(id: Int){
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
            getRecipeQuery(oldQuery)
        }
    }

}