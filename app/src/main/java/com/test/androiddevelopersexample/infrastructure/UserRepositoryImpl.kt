package com.test.androiddevelopersexample.infrastructure

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.test.androiddevelopersexample.domain.ResultWrapper
import com.test.androiddevelopersexample.domain.UserRepository
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistory
import com.test.androiddevelopersexample.ui.utils.Utils

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class UserRepositoryImpl(
    private val responseHandler: ResponseHandler,
    private val context: Context
): UserRepository {

    override suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<List<PurchaseHistory>> {
        val queryParams = HashMap<String, String>()
        queryParams[PAGE] = page.toString()
        queryParams[SIZE] = LIST_PAGES_SIZE

        Log.e("PAGINATED", page.toString())

        val json = when(page){
            1 -> Utils.loadJSONFromAsset(context, "purchase_orders_1.json")
            2 -> Utils.loadJSONFromAsset(context, "purchase_orders_2.json")
            3 -> Utils.loadJSONFromAsset(context, "purchase_orders_3.json")
            else -> null
        }

        val gSon = GsonBuilder().serializeNulls().create()
        val type = object : TypeToken<PurchaseHistoryResultEntityV2>() {

        }.type

        val purchases = gSon.fromJson<PurchaseHistoryResultEntityV2>(json, type)

        return responseHandler {
            val purchaseHistories = purchases.purchaseHistories
            purchaseHistories?.let {
                it.map { purchaseHistory ->
                    purchaseHistory.toPurchaseHistory()
                }
            } ?: emptyList()
        }
    }

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val LIST_PAGES_SIZE = "15"
    }
}
