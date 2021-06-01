package com.example.androiddevchallenge.mapper

import com.example.androiddevchallenge.model.Recipe
import com.example.androiddevchallenge.model.RecipeType
import com.example.androiddevchallenge.model.RecipeUiModel

fun Recipe.mapToUiModel() =
    RecipeUiModel(
        id,
        name,
        String.format("$ %.2f", price / 100),
        when (color) {
            RecipeType.PURPLE.color -> RecipeType.PURPLE
            RecipeType.LIGHT_GREEN.color -> RecipeType.LIGHT_GREEN
            RecipeType.RED.color -> RecipeType.RED
            RecipeType.YELLOW.color -> RecipeType.YELLOW
            else -> RecipeType.PURPLE
        }
    )