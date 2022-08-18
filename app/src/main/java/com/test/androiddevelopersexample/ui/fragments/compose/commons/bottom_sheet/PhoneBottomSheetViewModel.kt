package com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet

import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import com.test.androiddevelopersexample.ui.fragments.swipe.Country
import com.test.androiddevelopersexample.ui.fragments.swipe.MockCountries
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Martin Zarzar on 13/7/22.
 */
class PhoneBottomSheetViewModel
    :
    BaseViewModel<PhoneBottomSheetViewModel.ViewState, PhoneBottomSheetViewModel.Action>(ViewState()) {

    override val viewModelName: String = "PhoneBottomSheetViewModel"

    fun getCountryUrl() = "https://getapp-test.astropaycard.com/img/flags/CD.svg"

    fun emulateLoading() {
        sendAction(Action.Loading)
        lastIntention = { emulateLoading() }
        viewModelScope.launch {
            delay(3000)
            sendAction(Action.Success)
        }
    }

    fun emulateLoadCountry(json: String) {
        sendAction(Action.Loading)
        lastIntention = { emulateLoadCountry(json) }
        viewModelScope.launch {
            //delay(3000)
            val gSon = GsonBuilder().serializeNulls().create()
            val type = object : TypeToken<Country>() {}.type
            val country = gSon.fromJson<Country>(json, type)


            sendAction(Action.GetCountrySuccess(country = country))
        }
    }

    fun emulateLoadCountries(json: String) {
        sendAction(Action.Loading)
        lastIntention = { emulateLoadCountries(json) }
        viewModelScope.launch {
            //delay(3000)
            val gSon = GsonBuilder().serializeNulls().create()
            val type = object : com.google.gson.reflect.TypeToken<MockCountries>() {

            }.type
            val mockCountries = gSon.fromJson<MockCountries>(json, type)

            sendAction(Action.GetCountriesSuccess(countries = mockCountries.countries.toList()))
        }
    }

    fun emulateNetworkError() {
        sendAction(Action.Loading)
        viewModelScope.launch {
            delay(3000)
            sendAction(Action.NetworkError)
        }
    }

    fun onUpdatePhoneNumber(phoneNumber: String) {
        state = state.copy(
            phoneNumber = phoneNumber,
            isButtonEnabled = onCanEnableButton(phoneNumber)
        )
    }

    private fun onCanEnableButton(phoneNumber: String): Boolean {
        return phoneNumber.isNotEmpty()
    }

    fun openCountryList(open: Boolean){
        state = state.copy(
            viewCountryList = open
        )
    }

    fun onSelectCountry(country: Country){
        state = state.copy(
            country = country,
            viewCountryList = false
        )
    }

    fun onSerchCountry(text: String){
        state = state.copy(
            searchCountry = text
        )
    }

    fun onClearSearch(){
        state = state.copy(
            searchCountry = ""
        )
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.Loading -> state.copy(
            state = Type.LOAD_LIGHT
        )
        is Action.Success -> state.copy(
            state = Type.SHOW_CONTENT
        )
        is Action.GetCountrySuccess -> state.copy(
            state = Type.SHOW_CONTENT,
            country = viewAction.country
        )
        is Action.GetCountriesSuccess -> state.copy(
            state = Type.SHOW_CONTENT,
            countries = viewAction.countries
        )
        is Action.NetworkError -> state.copy(
            state = Type.NETWORK_ERROR
        )
    }

    data class ViewState(
        val state: Type = Type.NONE,
        val country: Country? = null,
        val countries: List<Country>? = null,
        val phoneNumber: String = "",
        val isButtonEnabled: Boolean = false,
        val viewCountryList: Boolean = false,
        val searchCountry: String = "",
    ) : BaseViewState

    sealed class Action : BaseAction {
        object Loading : Action()
        object Success : Action()
        class GetCountrySuccess(val country: Country) : Action()
        class GetCountriesSuccess(val countries: List<Country>) : Action()
        object NetworkError : Action()
    }
}
