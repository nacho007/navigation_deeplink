package com.test.androiddevelopersexample.infrastructure

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
import com.google.gson.annotations.SerializedName
import com.test.androiddevelopersexample.ui.fragments.compose.paginated.PurchaseHistory

data class PurchaseHistoryResultEntityV2(
    @SerializedName("page") val page: PageEntity,
    @SerializedName("data") val purchaseHistories: List<PurchaseHistoryEntityV2>?
)

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
    fun toPurchaseHistory(): PurchaseHistory {
        return PurchaseHistory(
            purchaseId ?: -1,
            PurchaseHistory.Status.values().first { it.name == status.uppercase() },
            name,
            currency,
            amount,
            date,
            expirationDate,
            pmImage,
            PurchaseHistory.Type.values().first { it.name == type.uppercase() }
        )
    }
}

data class PageEntity(
    @SerializedName("page_size") var pageSize: Int,
    @SerializedName("page_number") var pageNumber: Int,
    @SerializedName("total") var total: Int
)