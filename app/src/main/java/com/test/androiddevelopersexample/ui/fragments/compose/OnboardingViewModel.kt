package com.test.androiddevelopersexample.ui.fragments.compose

import androidx.lifecycle.viewModelScope
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel(

) : BaseViewModel<OnboardingViewModel.ViewState, OnboardingViewModel.Action>(ViewState()) {

    override val viewModelName: String = "CardsListViewModel"

    fun getCountryUrl() = "https://getapp-test.astropaycard.com/img/flags/CD.svg"

    fun emulateLoading() {
        sendAction(Action.Loading)
        lastIntention = { emulateLoading() }
        viewModelScope.launch {
            delay(3000)
            sendAction(Action.Success)
        }
    }

    fun emulateNetworkError() {
        sendAction(Action.Loading)
        viewModelScope.launch {
            delay(3000)
            sendAction(Action.NetworkError)
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            state = StateType.LOAD
        )
        Action.Success -> state.copy(
            state = StateType.HIDE
        )
        Action.NetworkError -> state.copy(
            state = StateType.NETWORK_ERROR
        )
    }

    data class ViewState(
        val state: StateType = StateType.NONE
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        object NetworkError : Action()
    }

}