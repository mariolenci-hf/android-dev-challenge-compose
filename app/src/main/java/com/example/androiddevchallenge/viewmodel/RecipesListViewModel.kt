package com.example.androiddevchallenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.mapper.mapToUiModel
import com.example.androiddevchallenge.model.Filter
import com.example.androiddevchallenge.model.Recipe
import com.example.androiddevchallenge.model.RecipeType
import com.example.androiddevchallenge.model.RecipesDataGenerator

class RecipesListViewModel : ViewModel() {

    private val _states = MutableLiveData<RecipesListViewState>()
    val state: LiveData<RecipesListViewState> get() = _states

    // getting data to display on the UI from the data source
    private val recipesList = mutableListOf<Recipe>()
    private val confirmation = mutableListOf<Int>()
    private val selectedTypes = mutableListOf<RecipeType>()

    init {
        updateState()
    }

    fun onIntent(intent: Intent) {
        Log.i("RecipesListViewModel", "intent: $intent")
        when (intent) {
            Intent.AddRecipe -> addRecipe()
            is Intent.ShowConfirmation -> showConfirmation(intent.id)
            is Intent.Delete -> delete(intent.id)
            is Intent.DismissConfirmation -> dismissConfirmation(intent.id)
            is Intent.SelectFilter -> selectFilter(intent.recipeType)
        }
    }

    private fun selectFilter(recipeType: RecipeType) {
        if (recipeType in selectedTypes) {
            selectedTypes.remove(recipeType)
        } else {
            selectedTypes.add(recipeType)
        }

        updateState()
    }

    private fun dismissConfirmation(recipeId: Int) {
        confirmation.removeIf { it == recipeId }
        updateState()
    }

    private fun delete(recipeId: Int) {
        confirmation.removeIf { it == recipeId }
        recipesList.removeIf { it.id == recipeId }
        updateState()
    }

    private fun showConfirmation(recipeId: Int) {
        if (recipeId in confirmation) return

        confirmation.add(recipeId)
        updateState()
    }

    private fun addRecipe() {
        recipesList.add(RecipesDataGenerator.generateRecipes(1).first())
        updateState()
    }

    private fun updateState() {
        val filteredList =
            recipesList.filter { recipe -> recipe.color in selectedTypes.map { it.color } || selectedTypes.isEmpty() }

        _states.value = RecipesListViewState(
            filteredList.map { it.mapToUiModel(showConfirmation = it.id in confirmation) },
            filteredList.map { it.price }.sum().takeIf { it > 0 }?.let { String.format("$ %.2f", it / 100) },
            RecipeType.values().map { Filter(it, it in selectedTypes) }
        )
    }
}