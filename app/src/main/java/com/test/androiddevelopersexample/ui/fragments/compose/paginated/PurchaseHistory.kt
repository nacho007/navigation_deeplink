package com.test.androiddevelopersexample.ui.fragments.compose.paginated

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
data class PurchaseHistory(
    val purchaseId: Int,
    val status: Status,
    val name: String,
    val currency: String,
    val amount: Double,
    val date: String,
    val expirationDate: String?,
    var image: String?,
    val type: Type
) {
    enum class Status {
        PENDING, CANCELED, CANCELLED, COMPLETED, APPROVED
    }

    enum class Type {
        APC, WALLET_BALANCE, PREPAID_CARD
    }
}