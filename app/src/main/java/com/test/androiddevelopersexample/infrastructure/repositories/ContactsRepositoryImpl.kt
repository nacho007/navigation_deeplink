package com.test.androiddevelopersexample.infrastructure.repositories

import android.content.Context
import android.provider.ContactsContract
import com.test.androiddevelopersexample.domain.actions.contacts.Contact
import com.test.androiddevelopersexample.domain.models.ResultWrapper
import com.test.androiddevelopersexample.domain.models.contacts.ContactWalletNumber
import com.test.androiddevelopersexample.domain.models.contacts.TransferredContacts
import com.test.androiddevelopersexample.domain.repositories.contacts.ContactsRepository
import com.test.androiddevelopersexample.infrastructure.network.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by ignaciodeandreisdenis on 10/8/22.
 */
class ContactsRepositoryImpl(
    private val responseHandler: ResponseHandler,
    private val context: Context
) : ContactsRepository {
    override suspend fun getContactsFromPhone(): List<Contact.Phone> {
        return withContext(Dispatchers.IO) {
            val contentResolver = context.contentResolver

            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER

            val cursor = contentResolver.query(
                uri,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                ),
                selection,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            try {
                val notRepeatedContacts = HashSet<Contact.Phone>()
                if (cursor != null) {
                    cursor.moveToFirst()
                    while (!cursor.isAfterLast) {
                        val contactNumber =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val contactName =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val photoUri =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                        val contact = Contact.Phone(contactName, contactNumber, photoUri)

                        if (!notRepeatedContacts.contains(contact)) {
                            notRepeatedContacts.add(contact)
                        }

                        cursor.moveToNext()
                    }
                    cursor.close()

                    notRepeatedContacts.toList()
                } else {
                    emptyList()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getTransferredContacts(): ResultWrapper<TransferredContacts> {
        return responseHandler {
            TransferredContacts(
                transferredContacts = listOf()
            )
        }
    }

    override suspend fun getTransferredWalletContacts(): ResultWrapper<List<ContactWalletNumber>> {
        return responseHandler {
            listOf(
                ContactWalletNumber(
                    number = "7474747474",
                    name = "Nacho 1"
                ),
                ContactWalletNumber(
                    number = "666",
                    name = "Nacho 2"
                ),
                ContactWalletNumber(
                    number = "007 007",
                    name = "Nacho 3"
                ),
                ContactWalletNumber(
                    number = "0987654123",
                    name = "Nacho 4"
                )
            )
        }
    }
}
