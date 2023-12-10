package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngrEntity(
    val name: String,
    val count: String
): Parcelable