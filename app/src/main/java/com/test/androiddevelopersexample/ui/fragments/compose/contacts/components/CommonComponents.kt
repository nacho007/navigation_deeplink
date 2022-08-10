package com.test.androiddevelopersexample.ui.fragments.compose.contacts.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.SecondaryButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.isScrollingUp
import com.test.androiddevelopersexample.ui.fragments.compose.commons.text_field.DefaultOutlinedTextField
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.setSubstringWithStyle

@Composable
fun NoPermissionsComponent(
    actionAllowAccessContacts: () -> Unit,
    actionAddPhoneNumberManually: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BodyText(
            text = stringResource(id = R.string.mobile_we_cant_show_your_contact_list),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        BodyText(
            text = stringResource(id = R.string.mobile_you_can_still_transfer_card),
            textAlign = TextAlign.Center
        )

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.mobile_allow_access_to_contacts).uppercase(),
            action = { actionAllowAccessContacts() }
        )

        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.mobile_add_phone_number_manually).uppercase(),
            action = { actionAddPhoneNumberManually() }
        )
    }
}

@Composable
fun HeaderInformation(
    scrollState: LazyListState,
    scrollOffset: Float,
    amountText: String,
) {
    AnimatedVisibility(
        visible = (scrollState.isScrollingUp() && scrollOffset > -10) || scrollOffset > -10,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BodyText(
                text = stringResource(
                    R.string.mobile_transfer_amount_to, amountText
                ).setSubstringWithStyle(
                    amountText,
                    SpanStyle(fontWeight = FontWeight.SemiBold)
                )
            )

            BodyText(
                text = stringResource(id = R.string.mobile_choose_who_to_send_money)
            )
        }
    }
}

@Composable
fun EmptyContactList(
    actionAddPhoneNumberManually: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BodyText(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.mobile_no_results_found),
            textAlign = TextAlign.Center
        )

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.mobile_add_phone_number_manually),
            action = {
                actionAddPhoneNumberManually()
            }
        )
    }
}

@Composable
fun SearchContact(
    searchText: String,
    onValueChange: (value: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            onValueChange = { onValueChange(it) },
            label = { BodyText(text = stringResource(R.string.mobile_search_for_a_contact)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun NoPermissionsComponentPreview() {
    AstroPayTheme {
        NoPermissionsComponent(
            actionAllowAccessContacts = {},
            actionAddPhoneNumberManually = {}
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun HeaderInformationPreview() {
    AstroPayTheme {
        HeaderInformation(
            scrollState = LazyListState(),
            scrollOffset = 0F,
            amountText = "UYU 450"
        )
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun EmptyContactListPreview() {
    AstroPayTheme {
        EmptyContactList {}
    }
}

@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun SearchContactPreview() {
    AstroPayTheme {
        SearchContact(searchText = "mai") {}
    }
}
