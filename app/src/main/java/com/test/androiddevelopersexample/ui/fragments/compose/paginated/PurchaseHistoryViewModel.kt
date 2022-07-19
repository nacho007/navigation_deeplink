package com.test.androiddevelopersexample.ui.fragments.compose.paginated

import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
internal class PurchaseHistoryViewModel :
    BaseViewModel<PurchaseHistoryViewModel.ViewState, PurchaseHistoryViewModel.Action>(
        ViewState()
    ) {

    override val viewModelName: String
        get() = "PurchaseHistoryViewModel"

    var lastState = Type.HIDE

    var page = 1
        private set

    override fun onLoadData() {
        onClearDestination()
        loadFirstPage()
    }

    private fun loadFirstPage() {
        this.page = 1
        state = state.copy(
            page = 1
        )
        sendAction(Action.Loading)
        loadPurchaseHistory(page)
    }

    fun loadNextPage() {
        this.page++
        sendAction(Action.LoadingNewPage)
        loadPurchaseHistory(page)
    }

    private fun loadPurchaseHistory(page: Int) {
        lastIntention = { loadPurchaseHistory(page) }

//        viewModelScope.launch {
//            logger.setLog(viewModelName, "$EVENT_LOGGER_CALLED_SERVICE ${getPurchaseHistory.name}")
//            getPurchaseHistory(page).also {
//                val action = when (it) {
//                    is GetPurchaseHistory.Result.Success -> {
//                        val movements = it.value
//                        Action.GetPurchaseHistorySuccess(purchaseHistoryResult = movements)
//                    }
//                    is GetPurchaseHistory.Result.Error -> Action.Failure(
//                        message = it.value?.description,
//                        retry = page == 1
//                    )
//                    GetPurchaseHistory.Result.NetworkError -> Action.NetworkError
//                }
//                sendAction(action)
//            }
//        }

    }


    fun onItemPressed(movement: PurchaseHistory) {
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
        is Action.GetPurchaseHistorySuccess -> {
            if (page == 1) {
                state.movements.clear()
            }
            state.movements.addAll(viewAction.purchaseHistoryResult)
            state.copy(
                loadState = if (page == 1 && viewAction.purchaseHistoryResult.isEmpty()) Type.EMPTY else Type.HIDE,
                destination = null,
                loadingNewPage = false
            )
        }
        is Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
            destination = null,
            loadingNewPage = false
        )
        is Action.LoadingNewPage -> state.copy(
            loadingNewPage = true,
            page = page
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE,
        val movements: MutableList<PurchaseHistory> = mutableListOf(),
        val page: Int = 1,
        val loadingNewPage: Boolean = false,
        val destination: Destination? = null
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        data class GetPurchaseHistorySuccess(val purchaseHistoryResult: List<PurchaseHistory>) :
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
}

data class LoadNotificationsError(
    val message: String? = null,
    val errorResourceId: Int,
    val networkError: Boolean = false,
    val retry: Boolean = false
)