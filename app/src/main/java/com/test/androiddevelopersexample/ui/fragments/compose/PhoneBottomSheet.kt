package com.test.androiddevelopersexample.ui.fragments.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.custom.Type
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.compose.commons.PhoneNumberTextField
import com.test.androiddevelopersexample.ui.fragments.swipe.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Created by Martin Zarzar on 8/7/22.
 */

sealed class PhoneBottomSheetData{
    data class InputPhoneData(val countryFlagsUrl:String):PhoneBottomSheetData()
    data class CountryListData(val countryFlagsUrl:String, val countries:List<Country>):PhoneBottomSheetData()
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneBottomSheet(
    phoneBottomSheetData:PhoneBottomSheetData?,
    screenState: @Composable (scope: CoroutineScope, bottomState: ModalBottomSheetState) -> Unit,
    phoneState: PhoneBottomSheetViewModel.ViewState,
    countryFlagsUrl: String,
    callback: (PhoneBottomSheetEvent) -> Unit = { }
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetContent = {
            when(phoneBottomSheetData){
                is PhoneBottomSheetData.InputPhoneData -> {
                    InputPhone(state = phoneState, countryFlagsUrl = countryFlagsUrl, callback = callback)
                }
                is PhoneBottomSheetData.CountryListData -> {
                    /*CountryList(
                        state = phoneState,
                        countryFlagsUrl = countryFlagsUrl,
                        scope = scope,
                        bottomState = modalBottomSheetState
                    )*/
                }
                else -> {
                    Text(text = "blabla")
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = colorResource(id = R.color.grey_800),
        scrimColor = colorResource(id = R.color.color_black_opacity_75),  // Color for the fade background when you open/close the drawer
    ) {
        screenState(
            scope = scope,
            bottomState = modalBottomSheetState
        )
    }
}

@Composable
fun InputPhone(
    state: PhoneBottomSheetViewModel.ViewState,
    countryFlagsUrl: String,
    callback: (PhoneBottomSheetEvent) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        state.country?.let { country ->
            PhoneNumberTextField(
                callingCode = country.callingCode,
                flagIcon = "${countryFlagsUrl}${country.iso}",
                hint = country.phoneHint,
                text = "",
                onTextChanged = {},
                changeCountry = {},
                onComplete = {}
            )
        }
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            text = "Next",
            action = {
                callback.invoke(PhoneBottomSheetEvent.LoadCountries)
            },
            enabled = true
        )
        Spacer(Modifier.size(90.dp))
    }
}
/*
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryList(
    state: PhoneBottomSheetViewModel.ViewState,
    scope: CoroutineScope,
    bottomState: ModalBottomSheetState,
    countryFlagsUrl: String
) {
    Scaffold(
        topBar = {
            AstroToolBar(title = "Select Country") {
                IconNavigationBack {
                    scope.launch {
                        bottomState.hide()
                    }
                }
            }
        },
        content = {
            state.countries?.let { items ->
                LazyColumn {
                    itemsIndexed(items) { i, it ->
                        CountryItem(country = it, countryFlagsUrl = countryFlagsUrl, onClick = {})
                    }
                }
            }
        })

}

@Composable
fun CountryItem(
    country: Country,
    countryFlagsUrl: String,
    onClick: () -> Unit
) {
    AstroCardView(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
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
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp)
                )
                Text(
                    text = "99999",//country.callingCode,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(end = 16.dp)
                )
                *//*              Box(modifier = Modifier.padding(16.dp)) {
                                  Icon(
                                      painter = painterResource(id = R.drawable.svg_check_gray),
                                      contentDescription = "Icon",
                                      tint = MaterialTheme.colors.primary
                                  )
                              }*//*
            }
        }
    }
}*/

sealed class PhoneBottomSheetEvent {
    object LoadCountries : PhoneBottomSheetEvent()
}

sealed class PhoneBottomSheetUI {
    data class InputPhone(
        val state: PhoneBottomSheetViewModel.ViewState,
        val countryFlagsUrl: String,
        val callback: (PhoneBottomSheetEvent) -> Unit = { }
    ) : PhoneBottomSheetUI()

    data class CountryList @OptIn(ExperimentalMaterialApi::class) constructor(
        val state: PhoneBottomSheetViewModel.ViewState,
        val scope: CoroutineScope,
        val bottomState: ModalBottomSheetState,
        val countryFlagsUrl: String
    ) : PhoneBottomSheetUI()


}

/*

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
private fun PhoneBottomSheetPreview() {
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
        val countries = listOf(country, country)

        PhoneBottomSheet(
            screenState = { scope, bottomState ->
                Button(onClick = {
                    scope.launch {
                        bottomState.show()
                    }
                }) {
                    Text(text = "BottomSheet")
                }
            },
            phoneState = PhoneBottomSheetViewModel.ViewState(
                state = Type.HIDE
            ),
            countryFlagsUrl = "https://getapp-test.astropaycard.com/img/flags/"
        )
    }
}
*/

/*@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
private fun CountryItemPreview() {
    AstroPayTheme {
        val country = DomainObjectsMocks.getCountry()
        val countries = listOf(country, country)
        CountryItem(
            country = country,
            onClick = {}
        )
    }
}*/
