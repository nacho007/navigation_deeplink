package com.test.androiddevelopersexample.infrastructure

import com.google.gson.Gson
import com.test.androiddevelopersexample.domain.ResultWrapper
import com.test.androiddevelopersexample.domain.UserRepository
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistory

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class UserRepositoryImpl(
    private val gSon: Gson,
    private val responseHandler: ResponseHandler
): UserRepository {
    override suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<List<PurchaseHistory>> {
        val queryParams = HashMap<String, String>()
        queryParams[PAGE] = page.toString()
        queryParams[SIZE] = LIST_PAGES_SIZE

        return responseHandler {
            listOf(
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
        }
    }

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val LIST_PAGES_SIZE = "15"
    }
}
