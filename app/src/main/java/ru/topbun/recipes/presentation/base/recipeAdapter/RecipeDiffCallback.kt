package ru.topbun.recipes.presentation.base.recipeAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.recipes.domain.entity.recipe.RecipeEntity

class RecipeDiffCallback: DiffUtil.ItemCallback<RecipeEntity>() {

    override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
        return oldItem == newItem
    }
}