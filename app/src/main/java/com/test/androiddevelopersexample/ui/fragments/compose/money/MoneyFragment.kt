package com.test.androiddevelopersexample.ui.fragments.compose.money

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.custom.ContentState
import com.test.androiddevelopersexample.ui.custom.Type
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.PhoneBottomSheet
import com.test.androiddevelopersexample.ui.fragments.compose.PhoneBottomSheetViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IconNavigationBack
import com.test.androiddevelopersexample.ui.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override var showBottomNavigation: Boolean = true

    private val viewModel: ComposeViewModel by viewModel()
    private val phoneViewModel: PhoneBottomSheetViewModel by viewModel()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                AstroPayTheme {
                    val screenState by viewModel.stateLiveData.observeAsState(initial = ComposeViewModel.ViewState())
                    val phoneState by phoneViewModel.stateLiveData.observeAsState(initial = PhoneBottomSheetViewModel.ViewState())

                    val bottomSheetState =
                        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                    val bottomSheetScope = rememberCoroutineScope()
                    val keyboardController = LocalSoftwareKeyboardController.current
                    if (bottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
                        DisposableEffect(Unit) {
                            onDispose {
                                keyboardController?.hide()
                            }
                        }
                    }
                    ModalBottomSheetLayout(
                        sheetContent = {
                            PhoneBottomSheet(
                                state = phoneState,
                                flagUrl = phoneViewModel.getCountryUrl(),
                                callback = {event -> onTestBottomSheet(event)}
                            )
                        },
                        sheetState = bottomSheetState,
                        scrimColor = colorResource(id = R.color.color_black_opacity_50),
                    ) {
                        Screen(
                            screenState = screenState,
                            scope = bottomSheetScope,
                            bottomState = bottomSheetState,
                            eventReducer = ::onUIEvent
                        )
                    }

                }
            }
        }
        viewModel.loadData()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Screen(
        screenState: ComposeViewModel.ViewState,
        scope: CoroutineScope,
        bottomState: ModalBottomSheetState,
        eventReducer: (UIEvent) -> Unit = {}
    ) {
        Scaffold(
            topBar = {
                AstroToolBar(title = "Este es mi texto") {
                    IconNavigationBack {
                        findNavController().popBackStack()
                    }
                }
            },
            content = {
                ContentState(
                    state = screenState.loadState,
                    lastIntention = { }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        AstroText(
                            text = "This is my title\nBigger than anyone",
                            modifier = Modifier.padding(all = 16.dp),
                            style = MaterialTheme.typography.h1
                        )

                        AstroCardView {
                            AstroText(
                                text = "Este es mi texto\nMore text",
                                modifier = Modifier.padding(all = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.padding(all = 32.dp))

                        DefaultButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            text = "Continues",
                            action = {}
                        )

                        DefaultButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            text = "Open Phone Buttom Sheet",
                            action = {
                                scope.launch {
                                    bottomState.show()
                                }
                            }
                        )
                    }
                }
            }
        )
    }

    internal sealed class UIEvent {
    }

    private fun onUIEvent(event: UIEvent) {
    }

    private fun onTestBottomSheet(event: PhoneBottomSheet){
        when(event){
            is PhoneBottomSheet.LoadCountries -> {
                val json =
                    Utils.loadJSONFromAsset(
                        requireContext(),
                        "countries.json"
                    )
                json?.let {
                    phoneViewModel.openCountryList(true)
                    phoneViewModel.emulateLoadCountries(it)
                }
            }
            is PhoneBottomSheet.OnChangePhone -> {
                phoneViewModel.onUpdatePhoneNumber(event.phone)
            }
            is PhoneBottomSheet.LoadCountry -> {
                val json = Utils.loadJSONFromAsset(requireContext(), "country.json")
                json?.let { phoneViewModel.emulateLoadCountry(it) }
            }
            is PhoneBottomSheet.CloseCountryList -> {
                phoneViewModel.openCountryList(false)
            }
            is PhoneBottomSheet.OnSelectCountry -> {
                phoneViewModel.onSelectCountry(event.country)
            }
            is PhoneBottomSheet.OnSearchCountry -> {
                phoneViewModel.onSerchCountry(event.text)
            }
            is PhoneBottomSheet.OnNextButton -> {
                //ToDO
            }
            is PhoneBottomSheet.OnClearSearch -> {
                phoneViewModel.onClearSearch()
            }
        }
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
    @OptIn(ExperimentalMaterialApi::class)
    private fun ComposeFragmentPreview() {
        val bottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val bottomSheetScope = rememberCoroutineScope()
        AstroPayTheme {
            Screen(
                screenState = ComposeViewModel.ViewState(loadState = Type.HIDE),
                scope = bottomSheetScope,
                bottomState = bottomSheetState
            )
        }
    }
}
