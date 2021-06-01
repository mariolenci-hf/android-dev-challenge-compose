package com.example.androiddevchallenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.mapper.mapToUiModel
import com.example.androiddevchallenge.model.Recipe
import com.example.androiddevchallenge.model.RecipesDataGenerator

class RecipesListViewModel : ViewModel() {

    private val _states = MutableLiveData<RecipesListViewState>()
    val state: LiveData<RecipesListViewState> get() = _states

    // getting data to display on the UI from the data source
    private val recipesList = mutableListOf<Recipe>()
    private val confirmation = mutableListOf<Int>()

    fun onIntent(intent: Intent) {
        Log.i("RecipesListViewModel", "intent: $intent")
        when (intent) {
            Intent.AddRecipe -> addRecipe()
            is Intent.ShowConfirmation -> showConfirmation(intent.id)
            is Intent.Delete -> delete(intent.id)
            is Intent.DismissConfirmation -> dismissConfirmation(intent.id)
        }
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
        _states.postValue(RecipesListViewState(
            recipesList.map { it.mapToUiModel(showConfirmation = it.id in confirmation) },
            recipesList.map { it.price }.sum().let { String.format("$ %.2f", it / 100) }
        ))
    }
}