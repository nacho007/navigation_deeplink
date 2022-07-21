package com.test.androiddevelopersexample.ui.fragments.compose.paginated.mock_preview

import androidx.compose.runtime.mutableStateListOf
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistoryViewModel

object PurchaseHistoryMockPreview {

    internal fun getMockState() = PurchaseHistoryViewModel.ViewState(
        movements = mutableStateListOf(
            PurchaseHistoryV2(
                purchaseId = 1,
                status = PurchaseHistoryV2.Status.APPROVED,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistoryV2.Type.APC
            ),
            PurchaseHistoryV2(
                purchaseId = 1,
                status = PurchaseHistoryV2.Status.PENDING,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistoryV2.Type.APC
            ),
            PurchaseHistoryV2(
                purchaseId = 1,
                status = PurchaseHistoryV2.Status.CANCELED,
                name = "LiteCoin",
                currency = "UYU",
                amount = 500.0,
                date = "2021-08-11T12:36:43Z",
                expirationDate = null,
                image = null,
                type = PurchaseHistoryV2.Type.APC
            ),
        )
    )
}