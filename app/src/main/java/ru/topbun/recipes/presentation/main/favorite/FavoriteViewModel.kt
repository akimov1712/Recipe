package ru.topbun.recipes.presentation.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListFavoriteRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getRecipeFavoriteListUseCase: GetListFavoriteRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val state get() = _state.asStateFlow()

    private fun getFavoriteRecipe() = viewModelScope.launch{
        getRecipeFavoriteListUseCase().collect {
            if (it.isEmpty()) {
                _state.value = FavoriteState.ErrorRecipe
            } else {
                _state.value = FavoriteState.RecipeList(it)
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

    init {
        getFavoriteRecipe()
    }

}