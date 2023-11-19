package ru.topbun.recipes.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.utils.getSeedForShuffle
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
): ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState>
        get() = _state

    private fun getRecipeList(){
        getRecipeUseCase("").observeForever {
            _state.value = HomeState.RecipeList(it.shuffled(Random(getSeedForShuffle())))
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