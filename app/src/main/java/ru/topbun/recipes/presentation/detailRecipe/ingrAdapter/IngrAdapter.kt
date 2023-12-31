package ru.topbun.recipes.presentation.detailRecipe.ingrAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.recipes.databinding.ItemIngrBinding
import ru.topbun.recipes.domain.entity.recipe.IngrEntity

class IngrAdapter: ListAdapter<IngrEntity, IngrViewHolder>(IngrDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngrBinding.inflate(inflater, parent, false)
        return IngrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngrViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvName.text = item.name
            tvCount.text = item.count
        }
    }
}