package ru.topbun.recipes.presentation.detailRecipe.stepDetailRecipe

import androidx.recyclerview.widget.DiffUtil

class StepRecipeDiffCallback: DiffUtil.ItemCallback<Pair<String, String>>() {

    override fun areItemsTheSame(
        oldItem: Pair<String, String>,
        newItem: Pair<String, String>
    ): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: Pair<String, String>,
        newItem: Pair<String, String>
    ): Boolean {
        return false
    }
}