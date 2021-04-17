package com.example.mniami.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mniami.models.FoodRecipe
import com.example.mniami.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}