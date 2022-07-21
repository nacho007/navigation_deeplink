package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.domain.actions.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.infrastructure.paging_sources.PurchaseHistorySource
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
            destination = null
        )
    }

    val purchaseHistory: Flow<PagingData<PurchaseHistoryV2>> = Pager(
        PagingConfig(
            pageSize = LIST_PAGES_SIZE
        )
    ) {
        PurchaseHistorySource(getPurchaseHistory)
    }.flow.cachedIn(viewModelScope)

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
            ),
            loadingNewPage = false
        )
        is Action.Loading -> {
            state.movements.clear()
            state.copy(
                loadState = Type.LOAD_LIGHT,
                destination = null,
                loadingNewPage = false
            )
        }
        is Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
            destination = null,
            loadingNewPage = false
        )
        else -> state.copy(
            loadState = Type.NETWORK_ERROR,
            destination = null,
            loadingNewPage = false
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE,
        val movements: MutableList<PurchaseHistoryV2> = mutableListOf(),
        val page: Int = 1,
        val loadingNewPage: Boolean = false,
        val destination: Destination? = null
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        data class GetPurchaseHistorySuccess(val purchaseHistoryResult: List<PurchaseHistoryV2>) :
            Action()

        data class Failure(
            val message: String? = null,
            val errorResourceId: Int = R.string.mobile_generic_error,
            val networkError: Boolean = false,
            val retry: Boolean = false
        ) : Action()

        object NetworkError : Action()
        object Loading : Action()
        object LoadingNewPage : Action()
    }

    sealed class Destination {
        data class ErrorDialog(val error: LoadNotificationsError) : Destination()
        data class PurchaseDetail(val id: Int) : Destination()
    }

    companion object {
        private const val LIST_PAGES_SIZE = 15
    }
}

data class LoadNotificationsError(
    val message: String? = null,
    val errorResourceId: Int,
    val networkError: Boolean = false,
    val retry: Boolean = false
)
