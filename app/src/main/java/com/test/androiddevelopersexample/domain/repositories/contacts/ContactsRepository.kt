package com.test.androiddevelopersexample.domain.repositories.contacts


import com.test.androiddevelopersexample.domain.actions.contacts.Contact
import com.test.androiddevelopersexample.domain.models.ResultWrapper
import com.test.androiddevelopersexample.domain.models.contacts.ContactWalletNumber
import com.test.androiddevelopersexample.domain.models.contacts.TransferredContacts

interface ContactsRepository {
    suspend fun getContactsFromPhone(): List<Contact.Phone>

    suspend fun getTransferredContacts(): ResultWrapper<TransferredContacts>

    suspend fun getTransferredWalletContacts(): ResultWrapper<List<ContactWalletNumber>>
}