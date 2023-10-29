package ru.topbun.recipes.presentation.main.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeListForCategoryUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.presentation.main.search.SearchState
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val getRecipeListForCategoryUseCase: GetRecipeListForCategoryUseCase
): ViewModel() {

    private val _state = MutableLiveData<CategoryState>()
    val state: LiveData<CategoryState>
        get() = _state

    fun getRecipeByCategory(category: String){
        getRecipeListForCategoryUseCase(category).observeForever {
            _state.value = CategoryState.RecipeList(it)
        }
    }

    fun updateFavoriteRecipe(id: Int) {
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
        }
    }

}