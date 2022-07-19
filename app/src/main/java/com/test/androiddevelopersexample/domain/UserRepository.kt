package com.test.androiddevelopersexample.domain

import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistory

interface UserRepository {
    suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<List<PurchaseHistory>>
}