package com.example.androiddevchallenge.viewmodel

import com.example.androiddevchallenge.model.RecipeType

sealed class Intent {
    object AddRecipe : Intent()
    data class ShowConfirmation(val id: Int) : Intent()
    data class DismissConfirmation(val id: Int) : Intent()
    data class Delete(val id: Int) : Intent()
    data class SelectFilter(val recipeType: RecipeType) : Intent()
}
