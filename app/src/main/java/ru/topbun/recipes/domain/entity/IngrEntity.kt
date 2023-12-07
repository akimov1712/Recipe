package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngrEntity(
    val ingrList: List<Pair<String, String>>,
): Parcelable
