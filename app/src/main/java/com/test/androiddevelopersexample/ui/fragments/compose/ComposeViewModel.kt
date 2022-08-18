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
            delay(2000)
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

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            loadState = Type.LOAD_LIGHT,
        )
        Action.Success -> state.copy(
            loadState = Type.SHOW_CONTENT,
        )
        Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE,
        val destination: Destination? = null
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()
    }

    sealed class Destination {
        object PaginatedList : Destination()
    }
}