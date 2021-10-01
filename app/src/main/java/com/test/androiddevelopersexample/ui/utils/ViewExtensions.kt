package com.test.androiddevelopersexample.ui.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Created by ignaciodeandreisdenis on 1/10/21.
 */

fun Fragment.navigate(destination: Int) {
    try {
        findNavController().navigate(destination)
    } catch (exception: Exception) {
        exception.printStackTrace()
    }
}