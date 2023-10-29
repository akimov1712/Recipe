package ru.topbun.recipes.presentation.main.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.recipes.databinding.ItemCategoryBinding

class CategoryAdapter: ListAdapter<String, CategoryViewHolder>(CategoryDiffCallback()) {

    var setOnCategoryClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvCategory.text = item
        holder.itemView.setOnClickListener {
            setOnCategoryClickListener?.invoke(item)
        }
    }

}