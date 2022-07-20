package com.test.androiddevelopersexample.domain

interface UserRepository {
    suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<PurchaseHistoryResultV2>
}