package com.test.androiddevelopersexample.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
data class ErrorResponse(
    val error: String,
    val description: String? = null,
    val errorInfo: ErrorInfo? = null
)

data class ErrorInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("message") val message: String,
    @SerializedName("key") val key: String
)