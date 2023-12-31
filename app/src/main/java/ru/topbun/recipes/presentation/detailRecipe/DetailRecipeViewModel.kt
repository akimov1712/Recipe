package ru.topbun.recipes.presentation.detailRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.ConnectException
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetDetailRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import javax.inject.Inject

@HiltViewModel
class DetailRecipeViewModel @Inject constructor(
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<DetailRecipeState>(DetailRecipeState.Loading)
    val state get() = _state.asStateFlow()

    fun getDetailRecipe(url: String) = viewModelScope.launch {
        _state.value = DetailRecipeState.Loading
        try {
            val recipe = getDetailRecipeUseCase(url)
            _state.value = DetailRecipeState.DetailRecipe(recipe)
        } catch (e: ConnectException){
        _state.value = DetailRecipeState.ErrorGetDetailRecipe
        }


    }

    fun getRecipe(recipeId: Int) = viewModelScope.launch {
        val recipe = getRecipeForIdUseCase(recipeId)
        _state.value = DetailRecipeState.RecipeItem(recipe)
    }


    fun updateFavoriteRecipe(id: Int) = viewModelScope.launch {
        val oldRecipe = getRecipeForIdUseCase(id)
        val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
        addRecipeUseCase(newRecipe)
        if (newRecipe.isFavorite) {
            _state.value = DetailRecipeState.ReplaceIconFavorite(true)
        } else {
            _state.value = DetailRecipeState.ReplaceIconFavorite(false)
        }

    }


}