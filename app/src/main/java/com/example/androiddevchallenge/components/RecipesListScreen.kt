package com.example.androiddevchallenge.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.mapper.mapToUiModel
import com.example.androiddevchallenge.model.Filter
import com.example.androiddevchallenge.model.RecipeType
import com.example.androiddevchallenge.model.RecipeUiModel
import com.example.androiddevchallenge.model.RecipesDataGenerator
import com.example.androiddevchallenge.ui.theme.DarkGray
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.Intent
import com.example.androiddevchallenge.viewmodel.RecipesListViewState

/**
 * Main task screen composable
 */
@ExperimentalFoundationApi
@Composable
fun RecipesListScreen(
    stateLiveData: LiveData<RecipesListViewState>,
    onIntent: (Intent) -> Unit = {}
) {
    val state by stateLiveData.observeAsState()

    Column {
        val current = state ?: return

        ColorFilter(filters = current.filterList, onIntent)

        if (current.recipesList.isEmpty()) {
            EmptyView(Modifier.weight(1f))
        } else {
            RecipeListView(
                Modifier.weight(1f),
                current.recipesList,
                onIntent
            )
        }

        BottomView(state?.price, onIntent)
    }
}

/**
 * Displays list of recipes
 */
@Composable
fun RecipeListView(
    modifier: Modifier,
    items: List<RecipeUiModel>,
    onIntent: (Intent) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.background(DarkGray)
    ) {
        items(items = items) { item ->
            when {
                item.showConfirmation -> ConfirmDeletionCard(item, onIntent)
                else -> RecipeCard(item, onIntent)
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

/**
 * Draws an "Add" button
 */
@Composable
fun AddButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Text(text = "Add recipe")
    }
}

/**
 * Card which displays a recipe with name, color and price
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeCard(
    recipeUiModel: RecipeUiModel,
    onIntent: (Intent) -> Unit = {}
) {
    Card(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = { onIntent(Intent.ShowConfirmation(recipeUiModel.id)) },
                onClick = {}
            )
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {
            val centerVerticalAlignment = Modifier.align(Alignment.CenterVertically)
            ColorView(color = recipeUiModel.type.color, centerVerticalAlignment)
            RecipeName(
                recipeUiModel.name,
                centerVerticalAlignment
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            VerticalDivider(centerVerticalAlignment)
            RecipePrice(recipeUiModel.price, centerVerticalAlignment)
        }
    }
}

/**
 * Card which shows a request to remove a particular recipe from the list
 */
@Composable
fun ConfirmDeletionCard(
    recipeUiModel: RecipeUiModel,
    onIntent: (Intent) -> Unit = {}
) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            Modifier
                .background(color = recipeUiModel.type.color)
                .padding(16.dp),
        ) {
            Text(
                text = "Remove from the list?",
                modifier = Modifier.padding(8.dp)
            )
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val weightModifier = Modifier.weight(1f)
                ConfirmationButton(text = "Yes", modifier = weightModifier) {
                    onIntent(Intent.Delete(recipeUiModel.id))
                }
                ConfirmationButton(text = "No", modifier = weightModifier) {
                    onIntent(Intent.DismissConfirmation(recipeUiModel.id))
                }
            }
        }
    }
}

/**
 * Small color indicator for a recipe
 */
@Composable
fun ColorView(color: Color, modifier: Modifier) {
    Spacer(
        modifier = modifier
            .width(8.dp)
            .height(52.dp)
            .background(color, shape = RoundedCornerShape(6.dp))
    )
}

/**
 * Displays a list of color tags available for filtering. Check [BonusComponentsReview]
 *
 * Use this view for Bonus task
 */
@ExperimentalFoundationApi
@Composable
fun ColorFilter(
    filters: List<Filter>,
    onIntent: (Intent) -> Unit = {}
) {
    Row(
        Modifier
            .background(DarkGray)
            .padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        filters.forEach { filter ->
            ColorView3(filter, onIntent)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
@ExperimentalFoundationApi
fun ColorView3(
    filter: Filter,
    onClick: (Intent) -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    val borderAnimation: Dp by animateDpAsState(
        if (filter.isSelected) 3.dp else 0.dp,
        tween(500)
    )

    Spacer(
        modifier = Modifier
            .width(64.dp)
            .height(24.dp)
            .background(filter.recipeType.color, shape = shape)
            .border(borderAnimation, Color.White, shape)
            .combinedClickable { onClick(Intent.SelectFilter(filter.recipeType)) }
    )
}

@ExperimentalFoundationApi
@Preview
@Composable
fun BonusComponentsReview() {
    MyTheme {
        ColorFilter(RecipeType.values().map { Filter(it, true) })
    }
}

@Preview
@Composable
fun ComponentsPreview() {
    MyTheme {
        Surface {
            val recipeUiModel = RecipesDataGenerator.generateRecipes(1).first().mapToUiModel()
            Column {
                RecipeCard(recipeUiModel)
                Spacer(modifier = Modifier.size(8.dp))
                ConfirmDeletionCard(recipeUiModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ScreenPreview() {
    val previewData = MutableLiveData(RecipesListViewState(RecipesDataGenerator.generateRecipes(5).map { it.mapToUiModel() }))
    MyTheme {
        RecipesListScreen(previewData)
    }
}