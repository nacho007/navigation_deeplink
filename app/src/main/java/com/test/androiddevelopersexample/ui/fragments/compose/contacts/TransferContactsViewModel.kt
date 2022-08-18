package com.test.androiddevelopersexample.ui.fragments.compose.contacts

import androidx.lifecycle.viewModelScope
import com.test.androiddevelopersexample.domain.actions.contacts.Contact
import com.test.androiddevelopersexample.domain.actions.contacts.GetContactsAPC
import com.test.androiddevelopersexample.domain.actions.contacts.GetContactsWallet
import com.test.androiddevelopersexample.ui.base.BaseAction
import com.test.androiddevelopersexample.ui.base.BaseViewModel
import com.test.androiddevelopersexample.ui.base.BaseViewState
import com.test.androiddevelopersexample.ui.base.GenericError
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import kotlinx.coroutines.launch
import java.util.*

internal class TransferContactsViewModel(
    private val getContactsWallet: GetContactsWallet,
    private val getContactsAPC: GetContactsAPC
) : BaseViewModel<TransferContactsViewModel.ViewState, TransferContactsViewModel.Action>(ViewState()) {

    override val viewModelName: String
        get() = "TransferWalletViewModel"
    private val allContacts = mutableListOf<Contact>()
    private var isFlowType: FlowType = FlowType.WALLET

    fun setFlowType(flowType: FlowType){
        isFlowType = flowType
    }

    fun loadContacts(isRefresh: Boolean = false) {
        sendAction(Action.Loading)
        lastIntention = { loadContacts() }
        viewModelScope.launch {
            if (isRefresh) {
                onClearSearchText()
                sendAction(Action.Refreshing)
            }
            when (isFlowType) {
                FlowType.WALLET -> {
                    getContactsWallet().also { result ->
                        when (result) {
                            is GetContactsWallet.Result.Error -> sendAction(Action.Failure(result.contacts))
                            is GetContactsWallet.Result.NetworkError -> sendAction(
                                Action.Failure(
                                    result.contacts
                                )
                            )
                            is GetContactsWallet.Result.Success -> {
                                allContacts.clear()
                                allContacts.addAll(result.recent)
                                allContacts.addAll(result.contacts)
                                sendAction(
                                    Action.GetContactsSuccess(
                                        recentContacts = result.recent,
                                        contacts = result.contacts
                                    )
                                )
                            }
                            is GetContactsWallet.Result.Nothing -> Action.Nothing
                        }
                    }
                }
                FlowType.APC -> {
                    getContactsAPC().also { result ->
                        when (result) {
                            is GetContactsAPC.Result.Error -> sendAction(Action.Failure(result.contacts))
                            is GetContactsAPC.Result.NetworkError -> sendAction(
                                Action.Failure(
                                    result.contacts
                                )
                            )
                            is GetContactsAPC.Result.Success -> {
                                allContacts.clear()
                                allContacts.addAll(result.recent)
                                allContacts.addAll(result.contacts)
                                sendAction(
                                    Action.GetContactsSuccess(
                                        recentContacts = result.recent,
                                        contacts = result.contacts
                                    )
                                )
                            }
                            is GetContactsAPC.Result.Nothing -> Action.Nothing
                        }
                    }
                }
            }
        }
    }

    fun onClearDestination() {
        state = state.copy(
            destination = null
        )
    }

    fun onSetSearchText(searchText: String) {
        val listFiltered = allContacts.filter {
            val name = (it.name ?: "").lowercase(Locale.getDefault())
            val number = it.number.lowercase(Locale.getDefault())
            val searchTerm = searchText.lowercase(Locale.getDefault())

            (name.contains(searchTerm) || number.contains(searchTerm))
        }

        state = state.copy(
            searchText = searchText,
            allContacts = listFiltered,
            destination = null
        )

    }

    private fun onClearSearchText() {
        state = state.copy(
            searchText = String()
        )
    }

    fun onBack() {
        state = state.copy(
            destination = Destination.Back
        )
    }

    fun onGoTransferCheckout(contact: Contact) {
        when (contact) {
            is Contact.Recent -> onRecentContactPressed()
            is Contact.Phone -> onContactPressed()
        }
        state = state.copy(
            destination = Destination.TransferCheckout(
                name = contact.name,
                number = contact.number,
                image = contact.image
            )
        )
    }

    fun onGoTransferCheckoutFromNewNumber(number: String){
        state = state.copy(
            destination = Destination.TransferCheckout(number = number)
        )
    }

    fun onOpenPermissionsRationaleDialog() {
        state = state.copy(
            destination = Destination.PermissionsRationaleDialog
        )
    }

    private fun onRecentContactPressed() {
    }

    private fun onContactPressed() {
    }

    fun onPermissionPositive() {
    }

    fun onPermissionNegative() {
    }

    fun onRequestPermission() {
    }

    fun refresh() {
        onClearDestination()
        loadContacts(isRefresh = true)
    }

    fun onPullToRefresh() {
    }

    fun newNumberFromSearchBar() {
    }

    fun newNumberFromNewNumber() {
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.Loading -> state.copy(
            loadState = Type.LOAD_LIGHT,
            allContacts = null,
            recentContacts = null,
            contacts = null
        )
        is Action.Refreshing -> state.copy(
            isRefreshing = true
        )
        is Action.GetContactsSuccess -> {
            state.copy(
                loadState = if (allContacts.isEmpty()) Type.EMPTY else Type.SHOW_CONTENT,
                allContacts = allContacts,
                recentContacts = viewAction.recentContacts,
                contacts = viewAction.contacts,
                isRefreshing = false
            )
        }
        is Action.Failure -> {
            state.copy(
                loadState = Type.SHOW_CONTENT,
                allContacts = viewAction.contactsFromPhone,
                recentContacts = null,
                contacts = viewAction.contactsFromPhone,
                isRefreshing = false
            )
        }
        is Action.Nothing -> state.copy(
            loadState = Type.SHOW_CONTENT,
            allContacts = null,
            recentContacts = null,
            contacts = null,
            isRefreshing = false
        )
    }

    internal sealed class Action : BaseAction {
        data class GetContactsSuccess(
            val recentContacts: List<Contact.Recent>,
            val contacts: List<Contact.Phone>
        ) : Action()

        data class Failure(val contactsFromPhone: List<Contact.Phone>) : Action()
        object Loading : Action()
        object Nothing : Action()
        object Refreshing : Action()
    }

    internal data class ViewState(
        val loadState: Type = Type.SHOW_CONTENT,
        val allContacts: List<Contact>? = null,
        val recentContacts: List<Contact.Recent>? = null,
        val contacts: List<Contact.Phone>? = null,
        val destination: Destination? = null,
        val isRefreshing: Boolean = false,
        val searchText: String = String()
    ) : BaseViewState

    sealed class Destination {
        object Back : Destination()
        data class ErrorDialog(val error: GenericError) : Destination()
        data class TransferCheckout(
            val number: String,
            val name: String? = null,
            val image: String? = null
        ) : Destination()
        object PermissionsRationaleDialog : Destination()
    }

    companion object {
        private const val EVENT_LOGGER_PULL_TO_REFRESH = "pull_to_refresh"
    }
}
