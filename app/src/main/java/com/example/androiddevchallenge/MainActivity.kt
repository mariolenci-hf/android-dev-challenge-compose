/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.components.RecipesListScreen
import com.example.androiddevchallenge.mapper.mapToUiModel
import com.example.androiddevchallenge.model.RecipesDataGenerator
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.RecipesListViewModel
import com.example.androiddevchallenge.viewmodel.RecipesListViewState

@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    val model: RecipesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { MyApp(model) }
    }
}

// Start building your app here!
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyApp(model: RecipesListViewModel) {
    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            RecipesListScreen(model.state, model::onIntent)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    val previewData = MutableLiveData(RecipesListViewState(RecipesDataGenerator.generateRecipes(5).map { it.mapToUiModel() }))
    MyTheme(darkTheme = true) {
        RecipesListScreen(previewData)
    }
}
