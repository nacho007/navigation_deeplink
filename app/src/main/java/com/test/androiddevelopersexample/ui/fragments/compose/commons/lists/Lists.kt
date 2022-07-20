package com.test.androiddevelopersexample.ui.fragments.compose.commons.lists

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.ui.fragments.compose.commons.swipe.DefaultSwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.isScrolledToEnd

@Composable
fun <Model> RefreshablePaginatedList(
    onRefresh: () -> Unit,
    items: MutableList<Model>,
    loadingNewPage: Boolean,
    page: Int,
    pageSize: Int,
    loadNextPage: () -> Unit,
    composable: @Composable LazyItemScope.(Model) -> Unit,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = onRefresh,
        indicator = { indicatorState, trigger ->
            DefaultSwipeRefreshIndicator(
                state = indicatorState,
                refreshTriggerDistance = trigger
            )
        }
    ) {
        val scrollState = rememberLazyListState()

        LazyColumn(
            state = scrollState
        ) {
            item { Spacer(Modifier.size(8.dp)) }
            itemsIndexed(items) { i, it ->
                composable(it)
                if (i == items.lastIndex && !loadingNewPage) {
                    Spacer(Modifier.size(8.dp))
                }
            }
            if (loadingNewPage) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
        val endOfListReached by remember {
            derivedStateOf {
                scrollState.isScrolledToEnd()
            }
        }
        Log.e("PAGINATED", endOfListReached.toString())
        if (endOfListReached) {
            if (items.size / pageSize >= page) {
                loadNextPage()
            }
        }
    }
}
