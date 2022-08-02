package com.test.androiddevelopersexample.infrastructure.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.androiddevelopersexample.domain.actions.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.models.Page
import com.test.androiddevelopersexample.domain.models.ResultWrapper
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryResultV2
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import com.test.androiddevelopersexample.domain.repositories.UserRepository
import org.apache.http.HttpException
import java.io.IOException

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class PurchaseHistorySource(
    private val userRepository: UserRepository
) : PagingSource<Int, PurchaseHistoryV2>() {

    override fun getRefreshKey(state: PagingState<Int, PurchaseHistoryV2>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PurchaseHistoryV2> {
        val nextPage = params.key ?: 1
        return when (val resultWrapper = userRepository.getPurchaseHistoryV2(nextPage)) {
            is ResultWrapper.Success -> {
                val prevKey = if (nextPage > 1) nextPage - 1 else null
                val purchaseHistories = resultWrapper.value.purchaseHistories
                val nextKey = if (purchaseHistories.isNotEmpty()) nextPage + 1 else null

                LoadResult.Page(
                    data = purchaseHistories,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            else -> LoadResult.Error(Exception())
        }
    }
}