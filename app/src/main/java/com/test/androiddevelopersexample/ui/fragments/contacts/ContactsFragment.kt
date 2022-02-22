package com.test.androiddevelopersexample.ui.fragments.contacts

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentContactsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseContactsFragment
import com.test.androiddevelopersexample.ui.utils.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.coroutines.*
import java.util.HashSet

/**
 * Created by ignaciodeandreisdenis on 22/2/22.
 */
class ContactsFragment :
    BaseContactsFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    override var screenTag = "ContactsFragment"

    private val allContacts = mutableListOf<Contact>()
    private val contactSection by lazy { Section().apply { setHideWhenEmpty(true) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnPermissions.setOnClickListener {
                requestContactsPermission()
            }

            rvContacts.apply {
                setHasFixedSize(true)
                adapter = groupAdapter
            }
        }
    }

    private val groupAdapter by lazy {
        GroupAdapter<GroupieViewHolder>().apply {
            add(contactSection)
        }
    }

    private fun populateContacts(list: List<Contact.Phone>) {
        contactSection.apply {
            clear()
            addAll(mapContactView(list))
        }
    }

    private fun mapContactView(
        list: List<Contact>,
        searchTerm: String = ""
    ): List<TransferContactView> {
        return list.mapIndexed { index, contact ->
            TransferContactView(contact, index, list.size, searchTerm)
        }
    }

    override val onContactsPermissionGranted: () -> Unit = {
        binding.apply {
            llNoPermissions.show(false)
            rvContacts.show(true)

            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                val contacts = getContactsFromPhone()
                populateContacts(contacts)
            }

        }
    }

    override val onContactsPermissionDenied: () -> Unit = {

    }


    private suspend fun getContactsFromPhone(): List<Contact.Phone> {
        return withContext(Dispatchers.IO) {
            val contentResolver = context?.contentResolver

            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER

            val cursor = contentResolver?.query(
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

    sealed class Contact {
        abstract val name: String?
        abstract val number: String
        abstract val image: String?

        data class Recent(
            override val name: String?,
            override val number: String,
            override val image: String?
        ) : Contact() {
            fun getFormattedNumber(): String {
                return number.replace(" ", "").replace("-", "").replace("+", "")
            }
        }

        data class Phone(
            override val name: String?,
            override val number: String,
            override val image: String?
        ) : Contact() {
            fun getFormattedNumber(): String {
                return number.replace(" ", "").replace("-", "").replace("+", "")
            }
        }
    }

}
