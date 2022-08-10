package com.test.androiddevelopersexample.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.Intention
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IntentionOrNull
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {

    protected abstract val viewModelName: String

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData = stateMutableLiveData.asLiveData()

    var lastIntentionOrNull: IntentionOrNull = null
        private set

    var lastIntention: Intention = { }
        set(value) {
            field = value
            lastIntentionOrNull = value
        }

    // Delegate will handle state event deduplication
    // (multiple states of the same type holding the same data will not be dispatched multiple times to LiveData stream)
    var state by Delegates.observable(initialState) { _, _, new ->
        stateMutableLiveData.value = new
    }

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    fun loadData() {
        onLoadData()
    }

    protected open fun onLoadData() {}

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}

interface BaseViewState

interface BaseAction

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

data class GenericError(val message: String? = null, val errorResourceId: Int)