package com.example.androiddevchallenge.viewmodel

sealed class Intent {
    object AddRecipe : Intent()
    data class ShowConfirmation(val id: Int) : Intent()
    data class DismissConfirmation(val id: Int) : Intent()
    data class Delete(val id: Int) : Intent()
}
