package com.test.androiddevelopersexample.ui.fragments.swipe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
data class MockCountries(
    @SerializedName("countries") val countries: ArrayList<Country>
) : Serializable
