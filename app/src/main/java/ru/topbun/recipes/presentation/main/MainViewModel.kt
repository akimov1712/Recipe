package ru.topbun.recipes.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListFavoriteRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.domain.useCases.InitRecipesUseCase
import ru.topbun.recipes.presentation.main.MainState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getListRecipeUseCase: GetListRecipeUseCase,
    private val getRecipeUseCase: GetRecipeUseCase,
    private val getRecipeFavoriteListUseCase: GetListFavoriteRecipeUseCase,
    private val initRecipesUseCase: InitRecipesUseCase
): ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState>
        get() = _state

    val recipeFavoriteList = getRecipeFavoriteListUseCase()

    fun getListRecipe(){
        getListRecipeUseCase().observeForever {
            _state.value = MainState.RecipeList(it)
        }
    }

    fun getRecipeQuery(query: String){
        getRecipeUseCase(query).observeForever {
            _state.value = MainState.RecipeList(it)
        }
    }

    fun updateFavoriteRecipe(id: Int){
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
        }
    }

    fun initRecipes(){
        viewModelScope.launch {
            initRecipesUseCase()
        }
    }

    init {
        getListRecipe()
    }

}