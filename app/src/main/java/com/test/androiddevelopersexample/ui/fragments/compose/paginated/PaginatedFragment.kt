package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs.CustomErrorDialog
import com.test.androiddevelopersexample.ui.fragments.compose.commons.progress.ProgressIndicator
import com.test.androiddevelopersexample.ui.fragments.compose.commons.swipe.DefaultSwipeRefreshIndicator
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.ContentState
import com.test.androiddevelopersexample.ui.fragments.compose.dialogs.ModalTransitionDialog
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.mock_preview.PurchaseHistoryMockPreview
import com.test.androiddevelopersexample.ui.utils.navigate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class PaginatedFragment :
    BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    private val viewModel: PurchaseHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                val screenState by viewModel.stateLiveData.observeAsState(initial = PurchaseHistoryViewModel.ViewState())
                AstroPayTheme {
                    Screen(
                        screenState = screenState,
                        eventReducer = ::onUIEvent,
                        purchaseList = viewModel.purchaseHistory
                    )
                }
            }
        }
    }

    @Composable
    private fun Screen(
        screenState: PurchaseHistoryViewModel.ViewState,
        eventReducer: (UIEvent) -> Unit = {},
        purchaseList: Flow<PagingData<PurchaseHistoryV2>>
    ) {
        screenState.destination?.let {
            Navigation(it)
        }

        ContentState(
            state = screenState.loadState,
            lastIntention = { }
        ) {
            Scaffold(
                content = {
                    PaginatedList(
                        purchaseList = purchaseList,
                        eventReducer = eventReducer
                    )
                }
            )
        }
    }

    @Composable
    private fun PaginatedList(
        purchaseList: Flow<PagingData<PurchaseHistoryV2>>,
        eventReducer: (UIEvent) -> Unit = {}
    ) {
        val purchaseListItems: LazyPagingItems<PurchaseHistoryV2> =
            purchaseList.collectAsLazyPagingItems()

        val swipeRefreshState = rememberSwipeRefreshState(
            isRefreshing = false
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh =
            {
                purchaseListItems.refresh()
            },
            indicator = { indicatorState, trigger ->
                DefaultSwipeRefreshIndicator(
                    state = indicatorState,
                    refreshTriggerDistance = trigger
                )
            }
        ) {
            LazyColumn {
                items(purchaseListItems) { item ->
                    item?.let {
                        PurchaseHistoryItem(
                            image = it.image,
                            type = it.type,
                            name = it.name,
                            status = it.status,
                            currency = it.currency,
                            amount = it.amount,
                            date = it.date,
                            onClick = { eventReducer(UIEvent.ItemClick(it)) }
                        )
                    }
                }

                purchaseListItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Row(
                                    modifier = Modifier.fillParentMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ProgressIndicator(color = Color.Yellow)
                                }
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { ProgressIndicator() }
                        }
                        loadState.append is LoadState.Error -> {
                            //You can use modifier to show error message
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Navigation(it: PurchaseHistoryViewModel.Destination) {
        when (it) {
            is PurchaseHistoryViewModel.Destination.ErrorDialog -> {
                val message =
                    it.error.message ?: stringResource(id = it.error.errorResourceId)
                ModalTransitionDialog(
                    onDismissRequest = viewModel::onClearDestination
                ) { modalTransitionDialogHelper ->
                    CustomErrorDialog(
                        description = message,
                        onConfirm = modalTransitionDialogHelper::triggerAnimatedClose
                    )
                }
            }
            is PurchaseHistoryViewModel.Destination.PurchaseDetail -> {
                navigate(PaginatedFragmentDirections.actionPaginatedFragmentToAstroCoinsFragment())
            }
        }
        viewModel.onClearDestination()
    }

    private fun onUIEvent(event: UIEvent) = when (event) {
        is UIEvent.ItemClick -> viewModel.onItemPressed(event.item)
    }

    internal sealed class UIEvent {
        data class ItemClick(val item: PurchaseHistoryV2) : UIEvent()
    }

    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        locale = ""
    )
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        locale = ""
    )
    @Composable
    private fun ScreenPreview() {
        AstroPayTheme {
            Screen(
                screenState = PurchaseHistoryMockPreview.getMockState(),
                eventReducer = {},
                purchaseList = flowOf(PagingData.empty())
            )
        }
    }
}
