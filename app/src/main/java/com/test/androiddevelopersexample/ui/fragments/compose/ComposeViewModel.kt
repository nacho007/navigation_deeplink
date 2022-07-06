package com.test.androiddevelopersexample.ui.fragments.compose

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
            loadState = Type.LOAD_LIGHT
        )

        viewModelScope.launch {
            delay(1500)
            state = state.copy(
                loadState = Type.HIDE
            )
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            loadState = Type.LOAD_LIGHT,
        )
        Action.Success -> state.copy(
            loadState = Type.HIDE,
        )
        Action.NetworkError -> state.copy(
            loadState = Type.NETWORK_ERROR,
        )
    }

    internal data class ViewState(
        val loadState: Type = Type.NONE
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()
    }
}