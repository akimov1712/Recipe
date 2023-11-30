package ru.topbun.recipes.presentation.main.recipeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.ItemRecipeBinding
import ru.topbun.recipes.domain.entity.RecipeModel
import javax.inject.Inject


class RecipeAdapter @Inject constructor():
    ListAdapter<RecipeModel, RecipeViewHolder>(RecipeDiffCallback()), View.OnClickListener {

    var setOnRecipeClickListener: ((String, String, Int) -> Unit)? = null
    var setOnFavoriteClickListener: ((Int) -> Unit)? = null

    override fun onClick(v: View?) {
        val recipe = v?.tag as RecipeModel
        when(v.id){
            R.id.btn_favorite -> setOnFavoriteClickListener?.invoke(recipe.id)
            else -> setOnRecipeClickListener?.invoke(recipe.urlFullRecipe, recipe.preview, recipe.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.btnFavorite.setOnClickListener(this)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        with(holder.binding){
            root.tag = recipe
            btnFavorite.tag = recipe

            tvName.text = recipe.name
            tvTime.text = recipe.time
            if (recipe.category.isBlank())tvCategory.text = "Неизвестно" else tvCategory.text = recipe.category
            if (recipe.time.isBlank()) tvTime.text = "Не известно" else tvTime.text = recipe.time
            Picasso.get().load(recipe.preview).into(ivPreview)
            if (recipe.isFavorite){
                btnFavorite.setImageResource(R.drawable.icon_favorite_enable)
            } else {
                btnFavorite.setImageResource(R.drawable.icon_favorite_disable)
            }
        }
    }
}