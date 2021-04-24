package com.example.mniami.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mniami.R
import com.example.mniami.models.ExtendedIngredient
import com.example.mniami.util.Constants.Companion.BASE_IMAGE_URL
import com.example.mniami.util.RecipeDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredients_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.ingredient_name.text = ingredientsList[position].name.capitalize()
        holder.itemView.ingredient_amount.text = ingredientsList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientsList[position].unit
        holder.itemView.ingredient_consistency.text = ingredientsList[position].consistency
        holder.itemView.ingredient_original.text = ingredientsList[position].original

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(ingredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipeDiffUtil(ingredientsList, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = ingredients

        diffUtilResult.dispatchUpdatesTo(this)
    }
}