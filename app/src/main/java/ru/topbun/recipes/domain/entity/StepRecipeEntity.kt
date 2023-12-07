package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepRecipeEntity(
    val stepRecipeList: List<Pair<String, String>>,
): Parcelable