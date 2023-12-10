package ru.topbun.recipes.presentation.detailRecipe.stepDetailRecipe

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.recipes.domain.entity.StepRecipeEntity

class StepRecipeDiffCallback: DiffUtil.ItemCallback<StepRecipeEntity>() {

    override fun areItemsTheSame(
        oldItem: StepRecipeEntity,
        newItem: StepRecipeEntity
    ): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: StepRecipeEntity,
        newItem: StepRecipeEntity
    ): Boolean {
        return false
    }
}