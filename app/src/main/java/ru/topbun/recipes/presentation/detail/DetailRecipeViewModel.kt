package ru.topbun.recipes.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.topbun.recipes.domain.useCases.AddRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetDetailRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetListRecipeUseCase
import ru.topbun.recipes.domain.useCases.GetRecipeUseCase
import ru.topbun.recipes.presentation.main.MainState
import javax.inject.Inject

class DetailRecipeViewModel @Inject constructor(
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase
): ViewModel() {

    private val _state = MutableLiveData<DetailRecipeState>()
    val state: LiveData<DetailRecipeState>
        get() = _state

    fun getDetailRecipe(url: String){
        viewModelScope.launch {
            val recipe = getDetailRecipeUseCase(url)
            recipe?.let { _state.value = DetailRecipeState.DetailRecipe(it) } ?: run {
                _state.value = DetailRecipeState.ErrorGetDetailRecipe
            }
        }
    }

}