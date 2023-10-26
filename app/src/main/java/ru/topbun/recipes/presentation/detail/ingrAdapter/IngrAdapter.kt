package ru.topbun.recipes.presentation.detail.ingrAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.recipes.databinding.ItemIngrBinding

class IngrAdapter: ListAdapter<Pair<String, String>, IngrViewHolder>(IngrDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngrViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngrBinding.inflate(inflater, parent, false)
        return IngrViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngrViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvName.text = item.first
            tvCount.text = item.second
        }
    }
}