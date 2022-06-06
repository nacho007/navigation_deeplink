package com.test.androiddevelopersexample.ui.fragments.compose

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class ComposeViewModel(
) : BaseViewModel<ComposeViewModel.ViewState, ComposeViewModel.Action>(ViewState()) {

    override val viewModelName: String = "ComposeViewModel"

    override fun onLoadData() {
        super.onLoadData()
        state = state.copy(
            animate = false
        )
        viewModelScope.launch {
            delay(500)
            state = state.copy(
                animate = true
            )
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            state = StateType.LOAD,
        )
        Action.Success -> state.copy(
            state = StateType.HIDE,
        )
        Action.NetworkError -> state.copy(
            state = StateType.NETWORK_ERROR,
        )
    }

    internal data class ViewState(
        val state: StateType = StateType.NONE,
        val animate: Boolean = false
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()
    }
}