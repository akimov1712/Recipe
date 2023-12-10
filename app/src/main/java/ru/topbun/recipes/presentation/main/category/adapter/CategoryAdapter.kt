package ru.topbun.recipes.presentation.main.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.topbun.recipes.databinding.ItemCategoryBinding
import ru.topbun.recipes.domain.entity.category.CategoryEntity

class CategoryAdapter: ListAdapter<CategoryEntity, CategoryViewHolder>(CategoryDiffCallback()) {

    var setOnCategoryClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameCategory.text = item.name
            Picasso.get().load(item.imageLink).into(ivPreview)
            holder.itemView.setOnClickListener {
                setOnCategoryClickListener?.invoke(item.name)
            }
        }

    }

}