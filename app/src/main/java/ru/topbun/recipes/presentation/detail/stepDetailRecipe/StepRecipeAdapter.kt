package ru.topbun.recipes.presentation.detail.stepDetailRecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.recipes.databinding.ItemStepRecipeBinding

class StepRecipeAdapter: ListAdapter<Map<String, String>, StepRecipeViewHolder>(StepRecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepRecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStepRecipeBinding.inflate(inflater, parent ,false)
        return StepRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepRecipeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){

        }
    }
}