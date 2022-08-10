package com.test.androiddevelopersexample.ui.fragments.compose.contacts.mock_preview

import com.test.androiddevelopersexample.domain.actions.contacts.Contact
import com.test.androiddevelopersexample.ui.fragments.compose.contacts.TransferContactsViewModel


object TransferContactsMockPreview {
    internal fun getMockState() = TransferContactsViewModel.ViewState(
        allContacts = listOf(
            Contact.Recent(
                name = "name recent 1",
                number = "3155553333",
                image = null
            ),
            Contact.Recent(
                name = "name recent 2",
                number = "3155553333",
                image = null
            ),
            Contact.Phone(
                name = "name 1",
                number = "3155553333",
                image = null
            ),
            Contact.Phone(
                name = "name 2",
                number = "3154442211",
                image = null
            )
        ),
        recentContacts = listOf(
            Contact.Recent(
                name = "name recent 1",
                number = "3155553333",
                image = null
            ),
            Contact.Recent(
                name = "name recent 2",
                number = "3155553333",
                image = null
            )
        ),
        contacts = listOf(
            Contact.Phone(
                name = "name 1",
                number = "3155553333",
                image = null
            ),
            Contact.Phone(
                name = "name 2",
                number = "3154442211",
                image = null
            )
        )
    )

    internal fun getMockStateEmpty() = TransferContactsViewModel.ViewState(
        allContacts = listOf(),
        recentContacts = listOf(),
        contacts = listOf()
    )
}
