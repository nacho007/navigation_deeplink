package com.test.androiddevelopersexample.ui.fragments.compose.crypto

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.text_field.DefaultTextField
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
class CryptoFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "CryptoFragment"

    override var showBottomNavigation: Boolean = true

    override val fragmentName: String
        get() = "CryptoFragment"
    override val screenName: String
        get() = "MoneyFragment"

    private val viewModel: ComposeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                val screenState by viewModel.stateLiveData.observeAsState(ComposeViewModel.ViewState())

                AstroPayTheme {
                    Screen(
                        screenState = screenState,
                        eventReducer = ::onUIEvent
                    )
                }
            }
        }
    }

    @Composable
    private fun Screen(
        screenState: ComposeViewModel.ViewState,
        eventReducer: (UIEvent) -> Unit
    ) {
        val hintValue = stringResource(id = R.string.mobile_zero_hint)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTextField(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                value =
                if (screenState.amountToPay.toString() != "null") {
                    Log.e(screenTag, screenState.amountToPay.toString())
                    screenState.amountToPay.toString()
                } else {
                    Log.e(screenTag, screenState.amountToPay.toString())
                    ""
                },
                onValueChange = { stringAmount ->
                    eventReducer(UIEvent.NewAmountToPay(stringAmount))
                },
                keyboardType = KeyboardType.Decimal,
                hint = hintValue,
                textAlign = TextAlign.Start,
                alignment = Alignment.CenterStart,
                fontSize = 20.sp
            )

            DefaultButton(modifier = Modifier.fillMaxWidth(), text = "My btn", action = {})
        }
    }

    private fun onUIEvent(event: UIEvent) {
        when (event) {
            is UIEvent.NewAmountToPay -> {
                viewModel.onNewAmountToPay(event.amountToPay)
            }
        }
    }

    private sealed class UIEvent {
        class NewAmountToPay(val amountToPay: String?) : UIEvent()
    }

    @Composable
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        locale = "es"
    )
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        locale = "es"
    )
    private fun CryptoFragmentPreview() {
        AstroPayTheme {
            Screen(
                screenState = ComposeViewModel.ViewState(loadState = Type.SHOW_CONTENT),
                eventReducer = {}
            )
        }
    }
}