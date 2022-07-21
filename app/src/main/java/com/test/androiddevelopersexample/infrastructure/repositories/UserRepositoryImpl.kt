package com.test.androiddevelopersexample.infrastructure.repositories

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.test.androiddevelopersexample.domain.models.purchase.PurchaseHistoryResultV2
import com.test.androiddevelopersexample.domain.models.ResultWrapper
import com.test.androiddevelopersexample.domain.repositories.UserRepository
import com.test.androiddevelopersexample.infrastructure.entities.PurchaseHistoryResultEntityV2
import com.test.androiddevelopersexample.infrastructure.network.ResponseHandler
import com.test.androiddevelopersexample.ui.utils.Utils
import kotlinx.coroutines.delay
import okhttp3.internal.wait

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
class UserRepositoryImpl(
    private val responseHandler: ResponseHandler,
    private val context: Context
) : UserRepository {

    override suspend fun getPurchaseHistoryV2(page: Int): ResultWrapper<PurchaseHistoryResultV2> {
        val queryParams = HashMap<String, String>()
        queryParams[PAGE] = page.toString()
        queryParams[SIZE] = LIST_PAGES_SIZE

        delay(2000)

        Log.e("PAGINATED", page.toString())

        val json = when (page) {
            1 -> Utils.loadJSONFromAsset(context, "purchase_orders_1.json")
            2 -> Utils.loadJSONFromAsset(context, "purchase_orders_2.json")
            3 -> Utils.loadJSONFromAsset(context, "purchase_orders_3.json")
            4 -> Utils.loadJSONFromAsset(context, "purchase_orders_4.json")
            else -> null
        }

        val gSon = GsonBuilder().serializeNulls().create()
        val type = object : TypeToken<PurchaseHistoryResultEntityV2>() {

        }.type

        val purchaseResult = gSon.fromJson<PurchaseHistoryResultEntityV2>(json, type)

        return responseHandler {
            purchaseResult.toPurchaseHistoryResultV2()
        }
    }

    companion object {
        private const val PAGE = "page"
        private const val SIZE = "size"
        private const val LIST_PAGES_SIZE = "15"
    }
}
