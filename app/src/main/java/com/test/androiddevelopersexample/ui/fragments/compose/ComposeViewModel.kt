package com.test.androiddevelopersexample.ui.fragments.compose

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class ComposeViewModel :
    BaseViewModel<ComposeViewModel.ViewState, ComposeViewModel.Action>(ViewState()) {

    override val viewModelName: String = "ComposeViewModel"

    override fun onLoadData() {
        super.onLoadData()

        state = state.copy(
            loadState = Type.LOAD_LIGHT
        )

        viewModelScope.launch {
            delay(500)
            state = state.copy(
                loadState = Type.SHOW_CONTENT
            )
        }
    }

    fun onClearDestination() {
        state = state.copy(
            destination = null
        )
    }

    fun onPaginatedPressed() {
        state = state.copy(
            destination = Destination.PaginatedList
        )
    }

    fun onLoadBlack() {
        state = state.copy(
            loadState = Type.LOAD_BLACK_OPACITY
        )

        Handler(Looper.getMainLooper()).postDelayed({
            state = state.copy(
                loadState = Type.SHOW_CONTENT
            )
        }, 2000)
    }

    fun onEmpty() {
        state = state.copy(
            loadState = Type.EMPTY
        )

        Handler(Looper.getMainLooper()).postDelayed({
            state = state.copy(
                loadState = Type.SHOW_CONTENT
            )
        }, 2000)
    }

    fun onNetworkError() {
        state = state.copy(
            loadState = Type.NETWORK_ERROR
        )

        Handler(Looper.getMainLooper()).postDelayed({
            state = state.copy(
                loadState = Type.SHOW_CONTENT
            )
        }, 2000)
    }

    fun onDialog() {
        state = state.copy(
            destination = Destination.Dialog
        )
    }

    fun onNewAmountToPay(amountToPay: String?) {
        state = state.copy(
            amountToPay = amountToPay
        )

        if (amountToPay != null) {
            onAmountChanged(
                amount = amountToPay
            )
        }
    }

    private fun onAmountChanged(
        amount: String
    ) {
        sendAction(Action.AmountToGetChangedSuccess(amount))
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.Loading -> state.copy(
            loadState = Type.LOAD_LIGHT,
        )
        is Action.Success -> state.copy(
            loadState = Type.SHOW_CONTENT,
        )
        is Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
        )
        is Action.AmountToGetChangedSuccess -> state.copy(
            loadState = Type.SHOW_CONTENT
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE,
        val destination: Destination? = null,
        val amountToPay: String? = null
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()

        data class AmountToGetChangedSuccess(
            val newAmount: String
        ) : Action()
    }

    sealed class Destination {
        object PaginatedList : Destination()
        object Dialog : Destination()
    }

}