package ru.topbun.recipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import javax.inject.Inject

class ViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getListRecipeUseCase: GetListRecipeUseCase,
    private val getRecipeUseCase: GetRecipeUseCase
): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun getListRecipe(){
        getListRecipeUseCase().observeForever {
            _state.value = State.RecipeList(it)
        }
    }

    fun getRecipeQuery(query: String){
        getRecipeUseCase(query).observeForever {
            _state.value = State.RecipeList(it)
        }
    }

}