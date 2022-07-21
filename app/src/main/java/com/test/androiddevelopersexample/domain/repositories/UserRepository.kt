package com.test.androiddevelopersexample.domain.repositories

import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryResultV2
import com.test.androiddevelopersexample.domain.models.ResultWrapper

interface UserRepository {
    suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<PurchaseHistoryResultV2>
}