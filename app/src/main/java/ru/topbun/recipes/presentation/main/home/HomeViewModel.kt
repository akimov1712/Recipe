package ru.topbun.recipes.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.entity.RecipeModel
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListFavoriteRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeListUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.domain.useCases.InitRecipesUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val getRecipeList: GetRecipeListUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val initRecipesUseCase: InitRecipesUseCase
): ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState>
        get() = _state

    private fun getRecipeList(){
        getRecipeList.invoke().observeForever {
            _state.value = HomeState.RecipeList(it)
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
        getRecipeList()
    }

}