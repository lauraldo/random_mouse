package com.niolasdev.randommouse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.niolasdev.randommouse.ui.theme.MainTheme

@Composable
fun CatHome(
    viewModel: CatsViewModel = hiltViewModel<CatsViewModel>(),
    modifier: Modifier
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle(
        initialValue = CatListState.Loading
    )

    CatHome(state, modifier)
}

@Composable
internal fun CatHome(
    uiState: CatListState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is CatListState.Data -> {
            LazyColumn {
                items(uiState.cats) { cat ->
                    Text(cat.id)
                }
            }
        }

        is CatListState.Error -> {
            ErrorMessage(uiState.message)
        }

        CatListState.Loading -> {
            Text("Loading")
        }
    }
}

@Composable
private fun ErrorMessage(error: String?) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(MainTheme.colorScheme.error)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Error during update ${error ?: ""}",
            color = MainTheme.colorScheme.onError
        )
    }
}