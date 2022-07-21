package com.test.androiddevelopersexample.domain.models.purchase

import com.test.androiddevelopersexample.domain.models.Page

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
data class PurchaseHistoryResultV2(
    val page: Page,
    val purchaseHistories: List<PurchaseHistoryV2>
)
