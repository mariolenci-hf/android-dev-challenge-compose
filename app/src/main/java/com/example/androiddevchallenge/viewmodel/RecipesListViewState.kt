package com.example.androiddevchallenge.viewmodel

import com.example.androiddevchallenge.model.Filter
import com.example.androiddevchallenge.model.RecipeType
import com.example.androiddevchallenge.model.RecipeUiModel

data class RecipesListViewState(
    val recipesList: List<RecipeUiModel>,
    val price: String? = null,
    val filterList: List<Filter> = listOf(
        Filter(RecipeType.YELLOW, false),
        Filter(RecipeType.RED, false),
        Filter(RecipeType.PURPLE, false),
        Filter(RecipeType.LIGHT_GREEN, false)
    )
)