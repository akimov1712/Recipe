package ru.topbun.recipes.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.recipes.domain.entity.RecipeModel

class RecipeDiffCallback: DiffUtil.ItemCallback<RecipeModel>() {

    override fun areItemsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeModel, newItem: RecipeModel): Boolean {
        return oldItem == newItem
    }
}