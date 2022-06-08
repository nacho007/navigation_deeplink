package com.test.androiddevelopersexample.ui.fragments.compose

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.custom.Type
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class ComposeViewModel(
) : BaseViewModel<ComposeViewModel.ViewState, ComposeViewModel.Action>(ViewState()) {

    override val viewModelName: String = "ComposeViewModel"

    override fun onLoadData() {
        super.onLoadData()

        state = state.copy(
            state = Type.LOAD_LIGHT
        )

        viewModelScope.launch {
            delay(1500)
            state = state.copy(
                state = Type.HIDE
            )
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            state = Type.LOAD_LIGHT,
        )
        Action.Success -> state.copy(
            state = Type.HIDE,
        )
        Action.NetworkError -> state.copy(
            state = Type.NETWORK_ERROR,
        )
    }

    internal data class ViewState(
        val state: Type = Type.NONE
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()
    }
}