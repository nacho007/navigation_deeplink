package com.test.androiddevelopersexample.domain.actions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.androiddevelopersexample.domain.actions.base.BaseAction
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.infrastructure.paging_sources.PurchaseHistorySource
import kotlinx.coroutines.flow.Flow

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class GetPurchaseHistory(
    private val purchaseHistorySource: PurchaseHistorySource
) : BaseAction() {

    override val name: String get() = "GetPurchaseHistory"

    operator fun invoke(): Flow<PagingData<PurchaseHistoryV2>> {
        return Pager(PagingConfig(pageSize = 10)) { purchaseHistorySource }.flow
    }
}
