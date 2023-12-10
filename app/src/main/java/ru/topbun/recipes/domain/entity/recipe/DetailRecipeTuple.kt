package ru.topbun.recipes.domain.entity.recipe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngrListTuple(
    val ingrList: List<IngrEntity>,
):Parcelable

@Parcelize
data class StepRecipeListTuple(
    val stepRecipeList: List<StepRecipeEntity>,
):Parcelable
