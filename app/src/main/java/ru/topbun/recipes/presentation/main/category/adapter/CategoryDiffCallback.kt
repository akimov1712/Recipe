package ru.topbun.recipes.presentation.main.category.adapter

import androidx.recyclerview.widget.DiffUtil

class CategoryDiffCallback: DiffUtil.ItemCallback<Pair<String, String>>() {

    override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
        return oldItem == newItem
    }
}