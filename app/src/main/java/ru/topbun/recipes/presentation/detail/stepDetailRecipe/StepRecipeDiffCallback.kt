package ru.topbun.recipes.presentation.detail.stepDetailRecipe

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

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