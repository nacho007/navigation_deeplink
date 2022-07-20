package com.test.androiddevelopersexample.infrastructure

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
import com.google.gson.annotations.SerializedName
import com.test.androiddevelopersexample.domain.Page
import com.test.androiddevelopersexample.domain.PurchaseHistoryResultV2
import com.test.androiddevelopersexample.domain.PurchaseHistoryV2

data class PurchaseHistoryResultEntityV2(
    @SerializedName("page") val page: PageEntity,
    @SerializedName("data") val purchaseHistories: List<PurchaseHistoryEntityV2>?
) {
    fun toPurchaseHistoryResultV2(): PurchaseHistoryResultV2 {
        return PurchaseHistoryResultV2(
            page = page.toPage(),
            purchaseHistories = purchaseHistories?.map {
                it.toPurchaseHistory()
            } ?: listOf()
        )
    }
}

data class PurchaseHistoryEntityV2(
    @SerializedName("purchase_id") val purchaseId: Int?,
    @SerializedName("pm_name") val name: String,
    @SerializedName("pm_img") val pmImage: String?,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("expiration_date") val expirationDate: String?,
    @SerializedName("created") val date: String,
) {
    fun toPurchaseHistory(): PurchaseHistoryV2 {
        return PurchaseHistoryV2(
            purchaseId ?: -1,
            PurchaseHistoryV2.Status.values().first { it.name == status.uppercase() },
            name,
            currency,
            amount,
            date,
            expirationDate,
            pmImage,
            PurchaseHistoryV2.Type.values().first { it.name == type.uppercase() }
        )
    }
}

data class PageEntity(
    @SerializedName("page_size") var pageSize: Int,
    @SerializedName("page_number") var pageNumber: Int,
    @SerializedName("total") var total: Int
) {
    fun toPage(): Page {
        return Page(
            pageSize = pageSize,
            pageNumber = pageNumber,
            total = total
        )
    }
}