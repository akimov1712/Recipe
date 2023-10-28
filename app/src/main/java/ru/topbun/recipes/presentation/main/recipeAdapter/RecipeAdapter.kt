package ru.topbun.recipes.presentation.main.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.ItemRecipeBinding
import ru.topbun.recipes.domain.entity.RecipeModel
import javax.inject.Inject


class RecipeAdapter @Inject constructor(): ListAdapter<RecipeModel, RecipeViewHolder>(
    RecipeDiffCallback()
) {

    var setOnRecipeClickListener: ((String, String) -> Unit)? = null
    var setOnFavoriteClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvName.text = item.name
            tvTime.text = item.time
            if (item.category.isBlank()){
                tvCategory.text = "Неизвестно"
            } else {
                tvCategory.text = item.category
            }
            if (item.time.isBlank()){
                tvTime.text = "Не известно"
            } else {
                tvTime.text = item.time
            }
            Picasso.get().load(item.preview).into(ivPreview)
            holder.itemView.setOnClickListener { setOnRecipeClickListener?.invoke(item.urlFullRecipe, item.preview) }
            if (item.isFavorite){
                btnFavorite.setImageResource(R.drawable.icon_favorite_enable)
            } else {
                btnFavorite.setImageResource(R.drawable.icon_favorite_disable)
            }
            btnFavorite.setOnClickListener {
                setOnFavoriteClickListener?.invoke(item.id)
            }
        }
    }
}