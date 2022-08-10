package com.test.androiddevelopersexample.domain.models.contacts

typealias ContactNumber = String

data class TransferredContacts(
        val transferredContacts: List<ContactNumber>?,
)

data class ContactWalletNumber(
        val number: String,
        val name: String?,
)