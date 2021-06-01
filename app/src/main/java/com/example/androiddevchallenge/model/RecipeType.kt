package com.example.androiddevchallenge.model

import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.ui.theme.Red300
import com.example.androiddevchallenge.ui.theme.Yellow800
import com.example.androiddevchallenge.ui.theme.lightGreen400
import com.example.androiddevchallenge.ui.theme.purple200

enum class RecipeType(val color: Color) {
    PURPLE(purple200),
    LIGHT_GREEN(lightGreen400),
    YELLOW(Yellow800),
    RED(Red300)
}