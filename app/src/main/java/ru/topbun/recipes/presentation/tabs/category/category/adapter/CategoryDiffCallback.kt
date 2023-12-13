package ru.topbun.recipes.presentation.tabs.category.category.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.recipes.domain.entity.category.CategoryEntity

class CategoryDiffCallback: DiffUtil.ItemCallback<CategoryEntity>() {

    override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }
}