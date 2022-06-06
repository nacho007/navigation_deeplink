package com.test.androiddevelopersexample.ui.fragments.swipe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
data class State(
    @SerializedName("code") val code: String,
    @SerializedName("displayName") val displayName: String
) : Serializable {
    override fun toString(): String {
        return displayName
    }
}