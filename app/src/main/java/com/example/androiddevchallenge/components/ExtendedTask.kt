package com.example.androiddevchallenge.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Recipe
import com.example.androiddevchallenge.model.RecipesDataGenerator


/**
 *
 * ======================================================
 *
 * PLEASE IGNORE FOR DEV CHALLENGE 2
 *
 * ======================================================
 */
@Composable
fun ExpandedCard(onClick: () -> Unit = {}) {
    Card(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .animateContentSize()
                .clickable { onClick() }
        ) {
            val recipe = Recipe(
                name = RecipesDataGenerator.names.random(),
                price = RecipesDataGenerator.randomPrice,
                color = RecipesDataGenerator.randomColor
            )
            ColorView2(color = recipe.color)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val centerVerticalAlignment = Modifier.align(Alignment.CenterVertically)
//                RecipeName(recipe, centerVerticalAlignment.weight(1f))
                PlusMinusButton(ButtonStyle.MINUS)
//                RecipePrice(recipe, centerVerticalAlignment)
                PlusMinusButton(ButtonStyle.PLUS)
            }
        }
    }
}

/**
 * Bonus part - add onClick logic here
 */
@Composable
private fun PlusMinusButton(style: ButtonStyle) {
    Button(
        modifier = Modifier.size(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { /*TODO*/ },
    ) {
        val text = when (style) {
            ButtonStyle.PLUS -> "+"
            ButtonStyle.MINUS -> "-"
        }
        Text(text = text, fontSize = 18.sp)
    }
}


@Composable
private fun ColorView2(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(86.dp)
            .background(color, shape = RoundedCornerShape(6.dp))
    )
}


enum class ButtonStyle {
    PLUS, MINUS
}

