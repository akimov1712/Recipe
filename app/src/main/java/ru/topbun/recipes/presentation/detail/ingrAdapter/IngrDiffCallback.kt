package ru.topbun.recipes.presentation.detail.ingrAdapter

import androidx.recyclerview.widget.DiffUtil

class IngrDiffCallback: DiffUtil.ItemCallback<Map<String, Int>>() {

    override fun areItemsTheSame(oldItem: Map<String, Int>, newItem: Map<String, Int>): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Map<String, Int>, newItem: Map<String, Int>): Boolean {
        return false
    }
}