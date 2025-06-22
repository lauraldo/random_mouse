package com.niolasdev.randommouse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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

    CatHome(
        uiState = state,
        onRefresh = { viewModel.refresh() },
        modifier = modifier
    )
}

@Composable
internal fun CatHome(
    uiState: CatListState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is CatListState.Data -> {
                Column {
                    Button(
                        onClick = onRefresh,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Refresh Cats")
                    }
                    
                    LazyColumn {
                        items(uiState.cats) { cat ->
                            Text(
                                text = cat.toString(),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            is CatListState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrorMessage(uiState.message)
                    Button(
                        onClick = onRefresh,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Retry")
                    }
                }
            }

            CatListState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text(
                            text = "Loading cats...",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
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
            text = "Error: ${error ?: "Unknown error"}",
            color = MainTheme.colorScheme.onError
        )
    }
}