package ru.topbun.recipes.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import ru.topbun.recipes.databinding.ItemRecipeBinding
import ru.topbun.recipes.domain.entity.RecipeModel
import kotlin.random.Random


class RecipeAdapter(private val context: Context): ListAdapter<RecipeModel, RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvName.text = item.name
            tvTime.text = "Время приготовления: ${item.time}"
            if (item.category.isNullOrBlank()){
                tvCategory.text = "Неизвестно"
            } else {
                tvCategory.text = "Категория: ${item.category}"
            }
            tvCountPortions.text = "Количество порций: " + Random.nextInt(1,5)
            Glide.with(context).load(item.preview).into(ivPreview)
        }
    }
}