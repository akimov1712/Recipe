package ru.topbun.recipes.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.presentation.main.MainState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getListRecipeUseCase: GetListRecipeUseCase,
    private val getRecipeUseCase: GetRecipeUseCase
): ViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState>
        get() = _state

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

    init {
        getListRecipe()
    }

}