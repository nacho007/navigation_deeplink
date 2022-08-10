package com.test.androiddevelopersexample.ui.fragments.compose.cards

import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
class CardsListFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "CardsListFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    Screen()
                }
            }
        }
    }

    @Composable
    private fun Screen(
        // should came from viewModel
        cards: MutableList<Int> = remember {
            mutableStateListOf(
                10, 10, 10, 10, 10,
                10, 10, 10, 10, 10,
                10, 10 ,10 ,10, 10,
                10, 10 ,10 ,10, 10
            )
        }
    ) {
        var isLoading by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.color_view_background)),
            content = { innerPadding ->
                Cards(
                    cards = cards,
                    innerPadding = innerPadding,
                    isLoading = isLoading,
                    updateToLoadingState = { isLoading = true },
                    removeLoadingState = { isLoading = false },
                )
            },
            bottomBar = {
                AddCardsBottomBar(cards)
            }
        )
    }

    @Composable
    private fun Cards(
        cards: MutableList<Int>,
        innerPadding: PaddingValues,
        isLoading: Boolean,
        updateToLoadingState: () -> Unit,
        removeLoadingState: () -> Unit
    ) {
//        val scrollState = rememberLazyGridState()
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//        ) {
//            LazyVerticalGrid(
//                modifier = Modifier.align(Alignment.TopCenter),
//                contentPadding = PaddingValues(8.dp),
//                state = scrollState
//            ) {
//                item {
//                    NewCardComponent()
//                }
//
//                items(cards) { cardAmount ->
//                    ApcCard(amount = cardAmount.toString())
//                }
//
//                item {
//                    Spacer(modifier = Modifier.height(40.dp))
//                }
//            }
//
//            AnimatedVisibility(
//                modifier = Modifier.align(Alignment.BottomCenter),
//                visible = isLoading && scrollState.isScrolledToEnd()
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//
//        // observer when reached end of list
//        val endOfListReached by remember {
//            derivedStateOf {
//                scrollState.isScrolledToEnd()
//            }
//        }
//
//        // act when end of list reached
//        LaunchedEffect(endOfListReached) {
//            if (cards.last() < 100) {
//                updateToLoadingState()
//                delay(1000)
//                addCards(cards)
//                removeLoadingState()
//            }
//        }
    }

    @Composable
    fun ApcCard(amount: String) {
        Card(
            modifier = Modifier
                .size(100.dp, 100.dp)
                .padding(4.dp),
            backgroundColor = Color.Black,
            contentColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = amount
                )
            }
        }
    }

    @Composable
    private fun AddCardsBottomBar(cards: MutableList<Int>) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                addCards(cards)
            }
        ) {
            Text(
                text = "Add More Cards"
            )
        }
    }

    private fun addCards(cards: MutableList<Int>) {
        val amountToAdd = cards.last() + 10
        if (amountToAdd < 100) {
            cards.addAll(
                listOf(
                    amountToAdd, amountToAdd, amountToAdd, amountToAdd, amountToAdd,
                    amountToAdd, amountToAdd, amountToAdd, amountToAdd, amountToAdd,
                    amountToAdd, amountToAdd, amountToAdd, amountToAdd, amountToAdd,
                    amountToAdd, amountToAdd, amountToAdd, amountToAdd, amountToAdd,
                )
            )
        } else if (amountToAdd == 100) {
            cards.addAll(
                listOf(amountToAdd, amountToAdd, amountToAdd)
            )
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun Preview() {
        MaterialTheme {
            Screen()
        }
    }

    override val fragmentName: String
        get() = "CardsFragment"
    override val screenName: String
        get() = "CardsFragment"
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
