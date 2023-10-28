package ru.topbun.recipes.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRecipeModel(
    val name: String,
    val category: String,
    val time: String,
    val countPortion: String,
    val kkal:String,
    val fats:String,
    val proteins:String,
    val carbohydrates:String,
    val ingrList: List<Pair<String, String>>,
    val stepRecipeList: List<Pair<String, String>>,
): Parcelable