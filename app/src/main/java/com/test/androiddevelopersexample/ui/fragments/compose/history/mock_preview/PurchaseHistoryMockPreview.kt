package com.test.androiddevelopersexample.ui.fragments.compose.history.mock_preview

import androidx.compose.runtime.mutableStateListOf
import com.test.androiddevelopersexample.ui.fragments.compose.history.PurchaseHistory
import com.test.androiddevelopersexample.ui.fragments.compose.history.PurchaseHistoryViewModel

object PurchaseHistoryMockPreview {

    internal fun getMockState() = PurchaseHistoryViewModel.ViewState(
        movements = mutableStateListOf(
            PurchaseHistory(
                purchaseId = 1,
                status = PurchaseHistory.Status.APPROVED,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistory.Type.APC
            ),
            PurchaseHistory(
                purchaseId = 1,
                status = PurchaseHistory.Status.PENDING,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistory.Type.APC
            ),
            PurchaseHistory(
                purchaseId = 1,
                status = PurchaseHistory.Status.CANCELED,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistory.Type.APC
            ),
        )
    )
}