package com.example.androiddevchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.model.RecipesDataGenerator

class RecipesListViewModel : ViewModel() {

//    val states = LiveData<>

    // getting data to display on the UI from the data source
    val recipesList = RecipesDataGenerator.generateRecipes(10)

    //String.format("$ %.2f", price / 100)
}