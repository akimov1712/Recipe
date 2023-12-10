package ru.topbun.recipes.domain.entity.recipe


data class DetailRecipeEntity(
    val name: String,
    val category: String,
    val time: String,
    val countPortion: String,
    val kkal:String,
    val fats:String,
    val proteins:String,
    val carbohydrates:String,
    val ingrList: List<IngrEntity>,
    val stepRecipeList: List<StepRecipeEntity>,
)