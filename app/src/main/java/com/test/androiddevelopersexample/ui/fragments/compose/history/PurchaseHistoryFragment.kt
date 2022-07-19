package com.test.androiddevelopersexample.ui.fragments.compose.history

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs.CustomErrorDialog
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.ContentState
import com.test.androiddevelopersexample.ui.fragments.compose.dialogs.ModalTransitionDialog
import com.test.androiddevelopersexample.ui.fragments.compose.history.mock_preview.PurchaseHistoryMockPreview
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class PurchaseHistoryFragment :
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
                        eventReducer = ::onUIEvent
                    )
                }
            }
        }
    }

    @Composable
    private fun Screen(
        screenState: PurchaseHistoryViewModel.ViewState,
        eventReducer: (UIEvent) -> Unit = {},
    ) {
        screenState.destination?.let {
            Navigation(it)
        }

        ContentState(
            state = screenState.loadState,
            lastIntention = { viewModel.lastIntention() }
        ) {
            Scaffold(
                content = {
//                    RefreshablePaginatedList(
//                        onRefresh = { eventReducer(UIEvent.Refresh) },
//                        items = screenState.movements,
//                        loadingNewPage = screenState.loadingNewPage,
//                        page = screenState.page,
//                        pageSize = LIST_PAGES_SIZE,
//                        loadNextPage = { eventReducer(UIEvent.LoadNewPage) }
//                    ) {
//                        PurchaseHistoryItem(
//                            image = it.image,
//                            type = it.type,
//                            name = it.name,
//                            status = it.status,
//                            currency = it.currency,
//                            amount = it.amount,
//                            date = it.date,
//                            onClick = { eventReducer(UIEvent.ItemClick(it)) }
//                        )
//                    }
                }
            )
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
//                val action =
//                    HistoryLogsFragmentDirections.actionHistoryLogsFragmentToPurchaseDetailFragment(
//                        it.id
//                    )
//                navigate(action)
            }
        }
        viewModel.onClearDestination()
    }

    private fun onUIEvent(event: UIEvent) = when (event) {
        is UIEvent.ItemClick -> viewModel.onItemPressed(event.item)
        is UIEvent.LoadNewPage -> viewModel.loadNextPage()
        is UIEvent.Refresh -> viewModel.loadData()
    }

    internal sealed class UIEvent {
        data class ItemClick(val item: PurchaseHistory) : UIEvent()
        object Refresh : UIEvent()
        object LoadNewPage : UIEvent()
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
                eventReducer = {}
            )
        }
    }

    companion object {
        private const val LIST_PAGES_SIZE = 15
    }
}
