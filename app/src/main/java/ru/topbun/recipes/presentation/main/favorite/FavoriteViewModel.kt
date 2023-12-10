package ru.topbun.recipes.presentation.main.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetListFavoriteRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getRecipeFavoriteListUseCase: GetListFavoriteRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val state get() = _state.asStateFlow()

    fun getFavoriteRecipe() = viewModelScope.launch{
        try {
            getRecipeFavoriteListUseCase().collect {
                Log.d("TAG", "Найдено -> $it")
                _state.value = FavoriteState.RecipeList(it)
            }
        } catch (e: NotFoundRecipesException){
            Log.d("TAG", "Не найдено")
            _state.value = FavoriteState.ErrorRecipe
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