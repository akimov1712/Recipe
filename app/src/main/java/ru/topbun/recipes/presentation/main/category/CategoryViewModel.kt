package ru.topbun.recipes.presentation.main.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.NotFoundRecipesException
import ru.topbun.recipes.domain.useCases.category.GetCategoryListUseCase
import ru.topbun.recipes.domain.useCases.recipe.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeForIdUseCase
import ru.topbun.recipes.domain.useCases.recipe.GetRecipeListForCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val getRecipeForIdUseCase: GetRecipeForIdUseCase,
    private val getRecipeListForCategoryUseCase: GetRecipeListForCategoryUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val state get() = _state.asStateFlow()

    private var categoryTitle = "Категория"

    fun getRecipeByCategory(category: String) = viewModelScope.launch{
        _state.value = CategoryState.Loading
        try {
            getRecipeListForCategoryUseCase(category).collect {
                _state.emit(CategoryState.RecipeList(it))
            }
        }catch (e: NotFoundRecipesException){
            _state.value = CategoryState.RecipeError
        }
    }

    fun updateFavoriteRecipe(id: Int) {
        viewModelScope.launch {
            val oldRecipe = getRecipeForIdUseCase(id)
            val newRecipe = oldRecipe.copy(isFavorite = !oldRecipe.isFavorite)
            addRecipeUseCase(newRecipe)
        }
    }

     private fun getCategoryList() = viewModelScope.launch {
         _state.value = CategoryState.Loading
        getCategoryListUseCase().collect{
            _state.value = CategoryState.CategoryList(it)
        }
    }

    fun saveCategoryTitle(category: String){
        categoryTitle = category
    }

    fun getCategoryTitle() = categoryTitle

    init{
        getCategoryList()
    }


}