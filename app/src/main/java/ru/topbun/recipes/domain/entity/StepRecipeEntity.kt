package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepRecipeEntity(
    val description: String,
    val imageLink: String
): Parcelable
