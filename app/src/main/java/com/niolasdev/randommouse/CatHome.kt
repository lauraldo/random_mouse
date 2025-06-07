package com.niolasdev.randommouse

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CatHome(
    viewModel: CatsViewModel,
    modifier: Modifier
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

//        viewModel.uiState.collectAsState() {
//
//        }
        viewModel.getCats()
    }
}