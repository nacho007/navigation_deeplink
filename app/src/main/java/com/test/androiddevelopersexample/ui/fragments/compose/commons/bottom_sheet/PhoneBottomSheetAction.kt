package com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey800
import com.test.androiddevelopersexample.ui.fragments.compose.commons.PhoneNumberTextField
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.SearchToolbar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import com.test.androiddevelopersexample.ui.fragments.swipe.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Martin Zarzar on 14/7/22.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneBottomSheet(
    state: PhoneBottomSheetViewModel.ViewState,
    bottomSheetScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    flagUrl: String,
    callback: (PhoneBottomSheetAction) -> Unit = { }
) {
    if (state.country == null) {
        callback.invoke(PhoneBottomSheetAction.LoadCountry)
    }

    if (state.viewCountryList) {
        CountryList(state = state, flagUrl = flagUrl, callback = callback)
    } else {
        AddPhone(
            state = state,
            bottomSheetScope = bottomSheetScope,
            bottomSheetState = bottomSheetState,
            flagUrl = flagUrl,
            callback = callback
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddPhone(
    state: PhoneBottomSheetViewModel.ViewState,
    bottomSheetScope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    flagUrl: String,
    callback: (PhoneBottomSheetAction) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Grey800 else Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        state.country?.let { country ->
            PhoneNumberTextField(
                callingCode = country.callingCode,
                flagIcon = "${flagUrl}${country.iso}",
                hint = country.phoneHint,
                text = state.phoneNumber,
                onTextChanged = { callback.invoke(PhoneBottomSheetAction.OnChangePhoneAction(it)) },
                changeCountry = {
                    callback.invoke(PhoneBottomSheetAction.LoadCountries)
                },
                onComplete = {}
            )
        }
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            text = "Next",
            action = {
                bottomSheetScope.launch {
                    bottomSheetState.hide()
                    callback.invoke(PhoneBottomSheetAction.OnNextButton)
                }
            },
            enabled = state.isButtonEnabled
        )
        Spacer(Modifier.size(90.dp))
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CountryList(
    state: PhoneBottomSheetViewModel.ViewState,
    flagUrl: String,
    callback: (PhoneBottomSheetAction) -> Unit = { }
) {
    Scaffold(
        topBar = {
            SearchToolbar(placeholderText = "Search Country",
                searchText = state.searchCountry,
                onClearClick = { callback.invoke(PhoneBottomSheetAction.OnClearSearch) },
                onSearchTextChanged = { callback.invoke(PhoneBottomSheetAction.OnSearchCountry(it)) }
            ) {
                IconNavigationBack {
                    callback.invoke(PhoneBottomSheetAction.CloseCountryList)
                }
            }
        }
    ) {
        state.countries?.let { countries ->
            val listFiltered = countries.filter {
                val name = it.name
                val callingCode = it.callingCode
                (name.lowercase(Locale.getDefault())
                    .contains(state.searchCountry.lowercase(Locale.getDefault())) ||
                        callingCode.lowercase(Locale.getDefault())
                            .contains(state.searchCountry.lowercase(Locale.getDefault())))
            }
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                itemsIndexed(listFiltered) { i, it ->
                    CountryItem(country = it, flagUrl = flagUrl, callback = callback)
                }
            }
        }
    }

}

@Composable
fun CountryItem(
    country: Country,
    flagUrl: String,
    callback: (PhoneBottomSheetAction) -> Unit = { }
) {
    DefaultCardView(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { callback.invoke(PhoneBottomSheetAction.OnSelectCountry(country = country)) }
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.svg_camera),
                        contentDescription = "Icon",
                        tint = MaterialTheme.colors.primary
                    )
                }

                Text(
                    text = country.name,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp)
                )
                Text(
                    text = country.callingCode,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(end = 16.dp)
                )
            }
        }
    }
}

sealed class PhoneBottomSheetAction {
    object LoadCountry : PhoneBottomSheetAction()
    object LoadCountries : PhoneBottomSheetAction()
    data class OnChangePhoneAction(val phone: String) : PhoneBottomSheetAction()
    data class OnSelectCountry(val country: Country) : PhoneBottomSheetAction()
    object CloseCountryList : PhoneBottomSheetAction()
    data class OnSearchCountry(val text: String) : PhoneBottomSheetAction()
    object OnClearSearch : PhoneBottomSheetAction()
    object OnNextButton : PhoneBottomSheetAction()
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@OptIn(ExperimentalMaterialApi::class)
private fun TestBottomSheetPreview() {
    AstroPayTheme {
        val country = Country(
            id = 0,
            iso = "UY",
            name = "Uruguay",
            callingCode = "598",
            phoneHint = "9xxxxxxx",
            documentTypeOptions = emptyList(),
            states = null,
            displayName = null
        )
        val bottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val bottomSheetScope = rememberCoroutineScope()
        PhoneBottomSheet(
            state = PhoneBottomSheetViewModel.ViewState(
                state = Type.SHOW_CONTENT,
                country = country
            ),
            bottomSheetScope = bottomSheetScope,
            bottomSheetState = bottomSheetState,
            flagUrl = ""
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@OptIn(ExperimentalMaterialApi::class)
private fun TestBottomSheetCountriesPreview() {
    AstroPayTheme {
        val country = Country(
            id = 0,
            iso = "UY",
            name = "Uruguay",
            callingCode = "598",
            phoneHint = "9xxxxxxx",
            documentTypeOptions = emptyList(),
            states = null,
            displayName = null
        )
        val countries = listOf(country, country, country, country, country)
        val bottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val bottomSheetScope = rememberCoroutineScope()
        PhoneBottomSheet(
            state = PhoneBottomSheetViewModel.ViewState(
                state = Type.SHOW_CONTENT,
                countries = countries,
                viewCountryList = true
            ),
            bottomSheetScope = bottomSheetScope,
            bottomSheetState = bottomSheetState,
            flagUrl = ""
        )
    }
}