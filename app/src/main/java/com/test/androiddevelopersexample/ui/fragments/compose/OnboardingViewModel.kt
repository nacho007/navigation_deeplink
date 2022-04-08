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
        viewModelScope.launch {
            delay(3000)
            sendAction(Action.Success)
        }
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        Action.Loading -> state.copy(
            isLoading = true,
            isSuccess = false
        )
        Action.Success -> state.copy(
            isLoading = false,
            isSuccess = true
        )
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
    }

}