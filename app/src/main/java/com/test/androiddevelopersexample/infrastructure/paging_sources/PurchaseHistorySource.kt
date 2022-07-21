package com.test.androiddevelopersexample.infrastructure.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.androiddevelopersexample.domain.actions.GetPurchaseHistory
import com.test.androiddevelopersexample.domain.models.Page
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryResultV2
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryV2
import org.apache.http.HttpException
import java.io.IOException

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class PurchaseHistorySource(
    private val getPurchaseHistory: GetPurchaseHistory
) : PagingSource<Int, PurchaseHistoryV2>() {

    override fun getRefreshKey(state: PagingState<Int, PurchaseHistoryV2>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PurchaseHistoryV2> {
        return try {
            val nextPage = params.key ?: 1
            var result = PurchaseHistoryResultV2(
                page = Page(0, 0, 0),
                purchaseHistories = listOf()
            )

            getPurchaseHistory(page = nextPage).also {
                when (it) {
                    is GetPurchaseHistory.Result.Success -> {
                        result = it.value
                    }
                    else -> {}
                }
            }

            LoadResult.Page(
                data = result.purchaseHistories,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (result.purchaseHistories.isEmpty()) null else result.page.pageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}