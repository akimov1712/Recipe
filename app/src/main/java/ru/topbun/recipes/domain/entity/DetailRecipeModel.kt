package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
data class DetailRecipeModel(
    val id: Int
): Parcelable