package ru.topbun.recipes.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetDetailRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import javax.inject.Inject

class DetailRecipeViewModel @Inject constructor(
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
): ViewModel() {

    private val _state = MutableLiveData<DetailRecipeState>()
    val state: LiveData<DetailRecipeState>
        get() = _state

    fun getDetailRecipe(url: String){
        viewModelScope.launch {
            val recipe = getDetailRecipeUseCase(url)
            recipe?.let { _state.value = DetailRecipeState.DetailRecipe(it) } ?: run {
                _state.value = DetailRecipeState.ErrorGetDetailRecipe
            }
        }
    }

    fun getRecipe(recipeId: Int){
        viewModelScope.launch {
            val recipe = getRecipeForIdUseCase(recipeId)
            _state.value = DetailRecipeState.RecipeItem(recipe)
        }
    }

    fun updateFavoriteRecipe(id: Int){
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
            if (newRecipe.isFavorite){
                _state.value = DetailRecipeState.ReplaceIconFavorite(true)
            } else {
                _state.value = DetailRecipeState.ReplaceIconFavorite(false)
            }

        }
    }

}