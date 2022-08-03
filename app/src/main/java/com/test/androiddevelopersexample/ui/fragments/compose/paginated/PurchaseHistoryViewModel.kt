package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import androidx.paging.PagingData
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.domain.actions.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import kotlinx.coroutines.flow.Flow

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
internal class PurchaseHistoryViewModel(
    private val getPurchaseHistory: GetPurchaseHistory
) : BaseViewModel<PurchaseHistoryViewModel.ViewState, PurchaseHistoryViewModel.Action>(
    ViewState()
) {
    override val viewModelName: String
        get() = "PurchaseHistoryViewModel"

    override fun onLoadData() {
        state = state.copy(
            loadState = Type.HIDE,
            destination = null,
            purchaseHistory = getPurchaseHistory()
        )
    }

    fun onItemPressed(movement: PurchaseHistoryV2) {
        if (movement.purchaseId != -1) {
            state = state.copy(
                destination = Destination.PurchaseDetail(id = movement.purchaseId)
            )
        }
    }

    fun onClearDestination() {
        state = state.copy(
            destination = null
        )
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.Failure -> state.copy(
            loadState = Type.HIDE,
            destination = Destination.ErrorDialog(
                LoadNotificationsError(
                    viewAction.message,
                    viewAction.errorResourceId,
                    viewAction.networkError,
                    viewAction.retry
                )
            )
        )
        is Action.Loading -> {
            state.copy(
                loadState = Type.LOAD_LIGHT,
                destination = null
            )
        }
        is Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
            destination = null
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE,
        val destination: Destination? = null,
        val purchaseHistory: Flow<PagingData<PurchaseHistoryV2>>? = null
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        data class Failure(
            val message: String? = null,
            val errorResourceId: Int = R.string.mobile_generic_error,
            val networkError: Boolean = false,
            val retry: Boolean = false
        ) : Action()

        object NetworkError : Action()
        object Loading : Action()
    }

    sealed class Destination {
        data class ErrorDialog(val error: LoadNotificationsError) : Destination()
        data class PurchaseDetail(val id: Int) : Destination()
    }
}

data class LoadNotificationsError(
    val message: String? = null,
    val errorResourceId: Int,
    val networkError: Boolean = false,
    val retry: Boolean = false
)
