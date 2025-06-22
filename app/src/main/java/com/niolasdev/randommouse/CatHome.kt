package com.niolasdev.randommouse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.niolasdev.randommouse.ui.theme.MainTheme
import com.niolasdev.randommouse.ui.widget.CatErrorState
import com.niolasdev.randommouse.ui.widget.CatList
import com.niolasdev.randommouse.ui.widget.CatLoadingAnimation

@Composable
fun CatHome(
    viewModel: CatsViewModel = hiltViewModel<CatsViewModel>(),
    modifier: Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle(
        initialValue = CatListState.Loading
    )

    CatHome(
        state = state,
        onRefresh = { viewModel.refresh() },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CatHome(
    state: CatListState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🐱 Random Cats",
                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                is CatListState.Data -> {
                    CatList(
                        cats = currentState.cats,
                        isLoading = false,
                        onRefresh = { onRefresh() }
                    )
                }

                is CatListState.Error -> {
                    CatErrorState(
                        message = currentState.message,
                        onRetry = { onRefresh() }
                    )
                }

                CatListState.Loading -> {
                    CatLoadingAnimation()
                }
            }
        }
    }
}