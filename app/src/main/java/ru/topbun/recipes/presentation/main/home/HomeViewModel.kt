package ru.topbun.recipes.presentation.main.home

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
import ru.topbun.recipes.utils.getSeedForShuffle
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
): ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state get() = _state.asStateFlow()

    private fun getRecipeList() = viewModelScope.launch{
        try {
            getRecipeUseCase("").collect {
                _state.value = HomeState.RecipeList(it.shuffled(Random(getSeedForShuffle())))
            }
        } catch (e: NotFoundRecipesException){
            _state.value = HomeState.ErrorRecipe("Рецепты не найдены")
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
        getRecipeList()
    }

}