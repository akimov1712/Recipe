package ru.topbun.recipes.presentation.detailRecipe.stepDetailRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.topbun.recipes.databinding.ItemStepRecipeBinding

class StepRecipeAdapter: ListAdapter<Pair<String, String>, StepRecipeViewHolder>(StepRecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepRecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStepRecipeBinding.inflate(inflater, parent ,false)
        return StepRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepRecipeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNumberStep.text = "${position + 1}. "
            tvTextRecipe.text = item.first
            if (item.second.isEmpty()){
                ivPreviewRecipe.visibility = View.GONE
            } else {
                Picasso.get().load(item.second).into(ivPreviewRecipe)
            }
        }
    }
}