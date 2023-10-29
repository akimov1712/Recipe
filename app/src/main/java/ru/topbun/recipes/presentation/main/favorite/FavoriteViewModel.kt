package ru.topbun.recipes.presentation.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListFavoriteRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getRecipeFavoriteListUseCase: GetListFavoriteRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
): ViewModel() {

    private val _state = MutableLiveData<FavoriteState>()
    val state: LiveData<FavoriteState>
        get() = _state

    private fun getFavoriteRecipe(){
        getRecipeFavoriteListUseCase().observeForever {
            _state.value = FavoriteState.RecipeList(it)
            if (it.isEmpty()) _state.value = FavoriteState.ErrorRecipe
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