package com.test.androiddevelopersexample.ui.fragments.swipe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
data class Country(
    @SerializedName("id") val id: Int,
    @SerializedName("iso") val iso: String,
    @SerializedName("name") val name: String,
    @SerializedName("calling_code") val callingCode: String,
    @SerializedName("phone_hint") val phoneHint: String,
    @SerializedName("documentTypeOptions") val documentTypeOptions: List<Document>,
    @SerializedName("states") val states: List<State>?,
    @SerializedName("refundCheck") val refundCheck: Boolean = false,
    @SerializedName("referralEnabled") val referralEnabled: Boolean = false,
    @SerializedName("displayName") val displayName: String?
) : Serializable