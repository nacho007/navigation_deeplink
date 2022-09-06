package com.test.androiddevelopersexample.ui.fragments.compose.money

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.test.androiddevelopersexample.domain.actions.contacts.TransferWalletParameter
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheet
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheetAction
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheetViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.ContentState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.FlowType
import com.test.androiddevelopersexample.ui.utils.Utils
import com.test.androiddevelopersexample.ui.utils.navigate
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
                                bottomSheetScope = bottomSheetScope,
                                bottomSheetState = bottomSheetState,
                                flagUrl = phoneViewModel.getCountryUrl(),
                                callback = { event -> onPhoneBottomSheet(event) }
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
        screenState.destination?.let {
            Navigation(it)
        }

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
                    lastIntention = { },
                    toolbar = {},
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(all = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            BodyText(
                                text = "This is my title\nBigger than anyone",
                                modifier = Modifier.padding(all = 16.dp),
                                style = MaterialTheme.typography.h1
                            )

                            DefaultCardView {
                                BodyText(
                                    text = "Este es mi texto\nMore text",
                                    modifier = Modifier.padding(all = 16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.padding(all = 16.dp))

                            DefaultButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "Jumio",
                                action = {
                                    scope.launch {
                                        eventReducer(UIEvent.OpenJumio)
                                    }
                                }
                            )

                            DefaultButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "Contact List",
                                action = {
                                    navigate(
                                        MoneyFragmentDirections.actionMoneyFragmentToTransferContactsFragment(
                                            transferWalletParameters = TransferWalletParameter(
                                                amount = 200.0,
                                                currency = "USD"
                                            ),
                                            transferAPCParameters = null,
                                            flowType = FlowType.WALLET
                                        )
                                    )
                                }
                            )

                            DefaultButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "Loaders",
                                action = {
                                    navigate(
                                        MoneyFragmentDirections.actionMoneyFragmentToLoaderFragment()
                                    )
                                }
                            )

                            DefaultButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "Open Phone Bottom Sheet",
                                action = {
                                    scope.launch {
                                        bottomState.show()
                                    }
                                }
                            )

                            DefaultButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                text = "Paginated",
                                action = {
                                    scope.launch {
                                        eventReducer(UIEvent.OpenPaginated)
                                    }
                                }
                            )
                        }
                    },
                    floatingButton = {}
                )
            }
        )
    }

    internal sealed class UIEvent {
        object OpenPaginated : UIEvent()
        object OpenJumio : UIEvent()
    }

    private fun onUIEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OpenPaginated -> viewModel.onPaginatedPressed()
            is UIEvent.OpenJumio -> startJumio()
        }
    }

    @Composable
    private fun Navigation(it: ComposeViewModel.Destination) {
        when (it) {
            is ComposeViewModel.Destination.PaginatedList -> {
                navigate(MoneyFragmentDirections.actionMoneyFragmentToPaginatedFragment())
            }
            else -> {}
        }
        viewModel.onClearDestination()
    }

    private fun onPhoneBottomSheet(event: PhoneBottomSheetAction) {
        when (event) {
            is PhoneBottomSheetAction.LoadCountries -> {
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
            is PhoneBottomSheetAction.OnChangePhoneAction -> {
                phoneViewModel.onUpdatePhoneNumber(event.phone)
            }
            is PhoneBottomSheetAction.LoadCountry -> {
                val json = Utils.loadJSONFromAsset(requireContext(), "country.json")
                json?.let { phoneViewModel.emulateLoadCountry(it) }
            }
            is PhoneBottomSheetAction.CloseCountryList -> {
                phoneViewModel.openCountryList(false)
            }
            is PhoneBottomSheetAction.OnSelectCountry -> {
                phoneViewModel.onSelectCountry(event.country)
            }
            is PhoneBottomSheetAction.OnSearchCountry -> {
                phoneViewModel.onSerchCountry(event.text)
            }
            is PhoneBottomSheetAction.OnNextButton -> {
                navigate(MoneyFragmentDirections.actionMoneyFragmentToPaginatedFragment())
            }
            is PhoneBottomSheetAction.OnClearSearch -> {
                phoneViewModel.onClearSearch()
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            processResult(result.resultCode, result.data?.extras?.getString("DESCRIPTION"))
        }


    private fun processResult(code: Int, description: String? = null) {

    }

    private fun startJumio() {
        val intent = Intent(requireContext(), Class.forName(JUMIO_PATH))
        activityResultLauncher.launch(intent)
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
                screenState = ComposeViewModel.ViewState(loadState = Type.SHOW_CONTENT),
                scope = bottomSheetScope,
                bottomState = bottomSheetState
            )
        }
    }

    override val fragmentName: String
        get() = "MoneyFragment"
    override val screenName: String
        get() = "MoneyFragment"
}

private const val JUMIO_PATH = "com.test.androiddevelopersexample.ui.fragments.jumio.MyJumioActivity"