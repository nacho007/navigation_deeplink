package com.test.androiddevelopersexample.ui.utils

import android.content.Context
import java.io.IOException
import java.nio.charset.StandardCharsets

object Utils {
    fun loadJSONFromAsset(context: Context, fileName: String?): String? {
        return try {
            val `is` = context.assets.open(fileName!!)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}