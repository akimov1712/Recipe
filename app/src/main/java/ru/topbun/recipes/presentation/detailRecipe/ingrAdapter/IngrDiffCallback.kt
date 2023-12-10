package ru.topbun.recipes.presentation.detailRecipe.ingrAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.recipes.domain.entity.recipe.IngrEntity

class IngrDiffCallback: DiffUtil.ItemCallback<IngrEntity>() {

    override fun areItemsTheSame(oldItem: IngrEntity, newItem: IngrEntity): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: IngrEntity, newItem: IngrEntity): Boolean {
        return false
    }
}