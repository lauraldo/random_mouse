package com.niolasdev.randommouse.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.niolasdev.randommouse.data.Cat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatList(
    cats: List<Cat>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onCatClick: (Cat) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh
    )
    Box(
        modifier = modifier.pullRefresh(pullRefreshState)
    ) {
        if (cats.isEmpty() && !isLoading) {
            CatEmptyState(onRefresh = onRefresh)
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = cats,
                    key = { cat -> cat.id }
                ) { cat ->
                    CatItem(
                        cat = cat,
                        onClick = onCatClick,
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}