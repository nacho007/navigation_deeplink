package com.test.androiddevelopersexample.domain.actions.contacts


import com.test.androiddevelopersexample.domain.actions.base.BaseAction
import com.test.androiddevelopersexample.domain.models.ErrorResponse
import com.test.androiddevelopersexample.domain.models.ResultWrapper
import com.test.androiddevelopersexample.domain.repositories.contacts.ContactsRepository
import java.io.Serializable

class GetContactsAPC(
    private val contactsRepository: ContactsRepository,
) : BaseAction() {
    override val name: String
        get() = "GetContacts"

    suspend operator fun invoke(): Result {
        val contactFromPhone =
            contactsRepository.getContactsFromPhone().sortedWith(compareBy { it.name })

        return when (val resultWrapper = contactsRepository.getTransferredContacts()) {
            is ResultWrapper.Success -> {
                val transferredContacts =
                    resultWrapper.value.transferredContacts?.distinctBy { it.replace(" ", "") }
                        ?: emptyList()
                val transferHistoryFormatted = transferredContacts.map {
                    it.replace(" ", "").replace("-", "").replace("+", "")
                }

                val allRecent = transferredContacts.map { Contact.Recent(null, it, null) }

                val recentWithContact = contactFromPhone.filter { contact ->
                    transferHistoryFormatted.contains(contact.getFormattedNumber())
                }.map {
                    Contact.Recent(it.name, it.number, it.image)
                }

                val contacts = contactFromPhone.filter { contact ->
                    transferHistoryFormatted.contains(contact.getFormattedNumber()).not()
                }

                val recentList = allRecent.map {
                    recentWithContact.find { withName ->
                        it.getFormattedNumber() == withName.getFormattedNumber()
                    } ?: run { it }
                }.distinct()
                Result.Success(recentList, contacts)
            }
            is ResultWrapper.NetworkError -> Result.NetworkError(contactFromPhone)
            is ResultWrapper.Error -> Result.Error(resultWrapper.error, contactFromPhone)
            is ResultWrapper.Void -> Result.Nothing
            else -> Result.Error(contacts = contactFromPhone)
        }
    }

    sealed class Result {
        data class Success(val recent: List<Contact.Recent>, val contacts: List<Contact.Phone>) : Result()
        data class Error(val value: ErrorResponse? = null, val contacts: List<Contact.Phone>) : Result()
        data class NetworkError(val contacts: List<Contact.Phone>) : Result()
        object Nothing : Result()
    }
}


sealed class Contact {
    abstract val name: String?
    abstract val number: String
    abstract val image: String?

    data class Recent(
        override val name: String?,
        override val number: String,
        override val image: String?
    ) : Contact()

    data class Phone(
        override val name: String?,
        override val number: String,
        override val image: String?
    ) : Contact()
}

fun Contact.getFormattedNumber(): String {
    return number.replace(" ", "").replace("-", "").replace("+", "")
}


data class TransferAPCParameter(
    val cardId: Int,
    var number: String = "",
    var name: String? = null,
    var image: String? = null,
    var isAstroPayUser: Boolean = false
) : Serializable