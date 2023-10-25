package ru.topbun.recipes.presentation.detail.stepDetailRecipe

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class StepRecipeDiffCallback: DiffUtil.ItemCallback<Map<String, String>>() {

    override fun areItemsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return false
    }
}