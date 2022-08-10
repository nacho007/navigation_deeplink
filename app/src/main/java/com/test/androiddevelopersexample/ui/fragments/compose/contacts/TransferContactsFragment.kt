package com.test.androiddevelopersexample.ui.fragments.compose.contacts

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.components.EmptyContactList
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.components.HeaderInformation
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.components.NoPermissionsComponent
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.components.SearchContact
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.domain.actions.contacts.Contact
import com.test.androiddevelopersexample.domain.actions.contacts.TransferAPCParameter
import com.test.androiddevelopersexample.domain.actions.contacts.TransferWalletParameter
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheet
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheetAction
import com.test.androiddevelopersexample.ui.fragments.compose.commons.bottom_sheet.PhoneBottomSheetViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs.CustomErrorDialog
import com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs.CustomPositiveNegativeDialog
import com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs.ModalTransitionDialog
import com.test.androiddevelopersexample.ui.fragments.compose.commons.isScrollingUp
import com.test.androiddevelopersexample.ui.fragments.compose.commons.swipe.DefaultSwipeRefreshIndicator
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.ContentState
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.components.ContactItem
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.mock_preview.TransferContactsMockPreview
import com.test.androiddevelopersexample.ui.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Serializable
import kotlin.math.min

@OptIn(ExperimentalPermissionsApi::class)
class TransferContactsFragment :
    BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {
    override val fragmentName = "TransferContactsFragment"
    override val screenName = SCREEN_NAME

    private val viewModel: TransferContactsViewModel by viewModel()
    private val phoneViewModel: PhoneBottomSheetViewModel by viewModel()
    private val args: TransferContactsFragmentArgs by navArgs()
    private var searchText = ""

    @OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                viewModel.setFlowType(args.flowType)

                val readContactsPermissionState = rememberPermissionState(
                    android.Manifest.permission.READ_CONTACTS
                )

                viewModel.onClearDestination()

                when {
                    readContactsPermissionState.status.isGranted ->
                        onUIEvent(UIEvent.PermissionsGranted)
                }

                AstroPayTheme {
                    val screenState by viewModel.stateLiveData.observeAsState(
                        initial = TransferContactsViewModel.ViewState()
                    )
                    val phoneState by phoneViewModel.stateLiveData.observeAsState(initial = PhoneBottomSheetViewModel.ViewState())
                    val bottomSheetState = rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden
                    )
                    val bottomSheetScope = rememberCoroutineScope()
                    val keyboardController = LocalSoftwareKeyboardController.current
                    if (bottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
                        DisposableEffect(Unit) {
                            onDispose {
                                keyboardController?.hide()
                            }
                        }
                    }

                    var amountText = ""

                    if (args.flowType == FlowType.WALLET) {
                        val walletParams = args.transferWalletParameters
                        walletParams?.let {
                            val amount =
                                (Utils.decimalFormatOnlyShowDecimalIfNotZero?.format(walletParams.amount)
                                    ?: walletParams.amount.toString())
                            amountText =
                                getString(R.string.two_values, walletParams.currency, amount)
                        }
                    }

                    ModalBottomSheetLayout(
                        sheetContent = {
                            PhoneBottomSheet(
                                state = phoneState,
                                bottomSheetScope = bottomSheetScope,
                                bottomSheetState = bottomSheetState,
                                flagUrl = phoneViewModel.getCountryUrl(),
                                callback = { event -> onPhoneBottomSheetEvent(event) }
                            )
                        },
                        sheetState = bottomSheetState,
                        scrimColor = colorResource(id = R.color.color_black_opacity_50),
                    ) {
                        Screen(
                            screenState = screenState,
                            permissionState = readContactsPermissionState,
                            flowType = args.flowType,
                            walletParams = args.transferWalletParameters,
                            apcParams = args.transferAPCParameters,
                            amountText = amountText,
                            hasPermission = readContactsPermissionState.status.isGranted,
                            bottomSheetScope = bottomSheetScope,
                            bottomSheetState = bottomSheetState,
                            eventReducer = ::onUIEvent,
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onClearDestination()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Screen(
        screenState: TransferContactsViewModel.ViewState,
        permissionState: PermissionState? = null,
        flowType: FlowType,
        walletParams: TransferWalletParameter? = null,
        apcParams: TransferAPCParameter? = null,
        amountText: String,
        hasPermission: Boolean,
        bottomSheetScope: CoroutineScope,
        bottomSheetState: ModalBottomSheetState,
        eventReducer: (UIEvent) -> Unit = {}
    ) {
        screenState.destination?.let {
            Navigation(
                destination = it, permissionState = permissionState,
                flowType = flowType,
                walletParams = walletParams,
                apcParams = apcParams
            )
        }

        val scrollState = rememberLazyListState()

        Scaffold(
            topBar = {
                AstroToolBar(
                    title = stringResource(id = R.string.mobile_transfer),
                ) {
                    IconNavigationBack {
                        eventReducer(UIEvent.Back)
                    }
                }
            },
            content = {
                ContentState(
                    state = screenState.loadState,
                    lastIntention = { viewModel.lastIntention() }
                ) {
                    Content(
                        screenState = screenState,
                        permissionState = permissionState,
                        bottomSheetScope = bottomSheetScope,
                        bottomSheetState = bottomSheetState,
                        flowType = flowType,
                        amountText = amountText,
                        scrollState = scrollState,
                        hasPermission = hasPermission,
                        eventReducer = eventReducer
                    )
                }
            },
            floatingActionButton = {
                if (hasPermission) {
                    AnimatedVisibility(
                        visible = (scrollState.isScrollingUp()),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        ExtendedFloatingActionButton(
                            onClick = {
                                viewModel.newNumberFromNewNumber()
                                bottomSheetScope.launch {
                                    bottomSheetState.show()
                                }
                            },
                            contentColor = Color.White,
                            backgroundColor = MaterialTheme.colors.primary,
                            text = {
                                BodyText(
                                    text = stringResource(id = R.string.mobile_new_number).uppercase(),
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            },
                            icon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = stringResource(id = R.string.mobile_new_number)
                                )
                            }
                        )
                    }


                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun Content(
        screenState: TransferContactsViewModel.ViewState,
        permissionState: PermissionState?,
        bottomSheetScope: CoroutineScope,
        bottomSheetState: ModalBottomSheetState,
        flowType: FlowType,
        amountText: String,
        scrollState: LazyListState,
        hasPermission: Boolean,
        eventReducer: (UIEvent) -> Unit = {}
    ) {
        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(screenState.isRefreshing),
            onRefresh = {
                viewModel.onPullToRefresh()
                viewModel.refresh()
            },
            indicator = { indicatorState, trigger ->
                DefaultSwipeRefreshIndicator(
                    state = indicatorState,
                    refreshTriggerDistance = trigger
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (hasPermission) {
                    screenState.allContacts?.let { contacts ->
                        stickyHeader {
                            HeaderInformation(
                                modifier = Modifier.fillMaxWidth(),
                                scrollState = scrollState,
                                amountText = amountText
                            )

                            SearchContact(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                searchText = screenState.searchText
                            ) {
                                searchText = it
                                eventReducer(
                                    UIEvent.SearchTextChange(
                                        it
                                    )
                                )
                            }
                        }

                        if (contacts.isEmpty()) {
                            item {
                                EmptyContactList(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ) {
                                    viewModel.newNumberFromSearchBar()
                                    bottomSheetScope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                            }
                        }

                        itemsIndexed(contacts) { i, contact ->
                            ContactItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                name = contact.name,
                                number = contact.number,
                                picture = contact.image,
                                isRecent = contact is Contact.Recent,
                                searchText = screenState.searchText,
                                onClick = { eventReducer(UIEvent.OpenContact(contact)) }
                            )

                            if (
                                i == contacts.size.minus(1) ||
                                i == (contacts.filterIsInstance<Contact.Recent>().size.minus(1))
                            ) {
                                Spacer(Modifier.height(16.dp))
                            }
                        }
                    }
                } else {
                    item {
                        NoPermissionsComponent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            actionAddPhoneNumberManually = {
                                viewModel.newNumberFromNewNumber()
                                bottomSheetScope.launch {
                                    bottomSheetState.show()
                                }
                            },
                            actionAllowAccessContacts = {
                                viewModel.onRequestPermission()

                                when (permissionState?.status) {
                                    is PermissionStatus.Denied -> {
                                        if (permissionState.status.shouldShowRationale) {
                                            onUIEvent(UIEvent.PermissionsRationale)
                                        } else {
                                            eventReducer(
                                                UIEvent.GetPermissions(
                                                    permissionState
                                                )
                                            )
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun Navigation(
        destination: TransferContactsViewModel.Destination,
        permissionState: PermissionState?,
        flowType: FlowType,
        walletParams: TransferWalletParameter? = null,
        apcParams: TransferAPCParameter? = null
    ) {
        when (destination) {
            is TransferContactsViewModel.Destination.Back -> findNavController().navigateUp()
            is TransferContactsViewModel.Destination.ErrorDialog -> {
                val message = destination.error.message
                    ?: stringResource(id = destination.error.errorResourceId)
                ModalTransitionDialog(onDismissRequest = viewModel::onClearDestination) { modalTransitionDialogHelper ->
                    CustomErrorDialog(
                        description = message,
                        onConfirm = modalTransitionDialogHelper::triggerAnimatedClose
                    )
                }
            }
            is TransferContactsViewModel.Destination.TransferCheckout -> {
                val name = destination.name
                val number = destination.number
                val image = destination.image
                when (flowType) {
                    FlowType.WALLET -> {

                    }
                    FlowType.APC -> {

                    }
                }
            }
            is TransferContactsViewModel.Destination.PermissionsRationaleDialog -> {
                ModalTransitionDialog(onDismissRequest = viewModel::onClearDestination) { modalTransitionDialogHelper ->
                    CustomPositiveNegativeDialog(
                        description = stringResource(R.string.mobile_permission_needed_contacts),
                        title = stringResource(R.string.mobile_contacts),
                        positiveText = stringResource(R.string.mobile_continue),
                        negativeText = stringResource(R.string.mobile_cancel),
                        onNegative = modalTransitionDialogHelper::triggerAnimatedClose,
                        onPositive = {
                            permissionState?.let { state ->
                                onUIEvent(UIEvent.GetPermissions(state))
                            }
                        }
                    )
                }
            }
        }
    }

    private sealed class UIEvent {
        object Refresh : UIEvent()
        data class GetPermissions(val permissionState: PermissionState) : UIEvent()
        object PermissionsNotGranted : UIEvent()
        object PermissionsGranted : UIEvent()
        object PermissionsRationale : UIEvent()
        object LoadContacts : UIEvent()
        data class SearchTextChange(val searchText: String) : UIEvent()
        data class OpenContact(val contact: Contact) : UIEvent()
        object Back : UIEvent()
    }

    private fun onUIEvent(event: UIEvent) {
        when (event) {
            is UIEvent.Back -> viewModel.onBack()
            is UIEvent.Refresh -> viewModel.refresh()
            is UIEvent.GetPermissions -> event.permissionState.launchPermissionRequest()
            is UIEvent.SearchTextChange -> viewModel.onSetSearchText(event.searchText)
            is UIEvent.OpenContact -> viewModel.onGoTransferCheckout(event.contact)
            is UIEvent.PermissionsNotGranted -> {
                viewModel.onPermissionNegative()
                weCantContinueToast()
            }
            is UIEvent.PermissionsGranted -> {
                viewModel.onPermissionPositive()
                viewModel.loadContacts()
            }
            is UIEvent.LoadContacts -> {
                viewModel.loadContacts()
            }
            is UIEvent.PermissionsRationale -> viewModel.onOpenPermissionsRationaleDialog()
        }
    }

    private fun onPhoneBottomSheetEvent(event: PhoneBottomSheetAction) {
        when (event) {
            is PhoneBottomSheetAction.LoadCountries -> {
                phoneViewModel.openCountryList(true)
            }
            is PhoneBottomSheetAction.OnChangePhoneAction -> {
                phoneViewModel.onUpdatePhoneNumber(event.phone)
            }
            is PhoneBottomSheetAction.LoadCountry -> {
                phoneViewModel.loadData()
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
            }
            is PhoneBottomSheetAction.OnClearSearch -> {
                phoneViewModel.onClearSearch()
            }
        }
    }

    companion object {
        private const val SCREEN_NAME = "TRANSFER_CONTACTS"
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
    @OptIn(ExperimentalMaterialApi::class)
    private fun NoPermissionPreview() {
        AstroPayTheme {
            val bottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val bottomSheetScope = rememberCoroutineScope()

            Screen(
                screenState = TransferContactsMockPreview.getMockState(),
                eventReducer = {},
                flowType = FlowType.WALLET,
                walletParams = TransferWalletParameter(
                    amount = 250000.00,
                    currency = "COP",
                    name = "John Lennon",
                    number = "12345678"
                ),
                apcParams = null,
                amountText = "COP $250.000",
                hasPermission = false,
                bottomSheetScope = bottomSheetScope,
                bottomSheetState = bottomSheetState
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
    @OptIn(ExperimentalMaterialApi::class)
    private fun WalletTransferWithPermissionPreview() {
        AstroPayTheme {
            val bottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val bottomSheetScope = rememberCoroutineScope()

            Screen(
                screenState = TransferContactsMockPreview.getMockState(),
                eventReducer = {},
                flowType = FlowType.WALLET,
                walletParams = TransferWalletParameter(
                    amount = 250000.00,
                    currency = "COP",
                    name = "John Lennon",
                    number = "12345678"
                ),
                apcParams = null,
                amountText = "COP $250.000",
                hasPermission = true,
                bottomSheetScope = bottomSheetScope,
                bottomSheetState = bottomSheetState
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
    @OptIn(ExperimentalMaterialApi::class)
    private fun ScreenEmptyListPreview() {
        AstroPayTheme {
            val bottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val bottomSheetScope = rememberCoroutineScope()
            Screen(
                screenState = TransferContactsMockPreview.getMockStateEmpty(),
                eventReducer = {},
                flowType = FlowType.WALLET,
                walletParams = null,
                apcParams = null,
                amountText = "COP $250.000",
                hasPermission = true,
                bottomSheetScope = bottomSheetScope,
                bottomSheetState = bottomSheetState
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
    @OptIn(ExperimentalMaterialApi::class)
    private fun APCTransferWithPermissionPreview() {
        AstroPayTheme {
            val bottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val bottomSheetScope = rememberCoroutineScope()

            Screen(
                screenState = TransferContactsMockPreview.getMockState(),
                eventReducer = {},
                flowType = FlowType.APC,
                apcParams = TransferAPCParameter(
                    cardId = 1,
                    number = "1234 1234 1234 1234",
                    name = "John Lennon",
                    image = "Image",
                    isAstroPayUser = true
                ),
                walletParams = null,
                amountText = "USD $25",
                hasPermission = true,
                bottomSheetScope = bottomSheetScope,
                bottomSheetState = bottomSheetState
            )
        }
    }
}

enum class FlowType : Serializable {
    APC, WALLET
}