package com.test.androiddevelopersexample.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

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

fun View.show(show: Boolean) {
    visibility = View.VISIBLE.takeIf { show } ?: View.GONE
}

fun GroupAdapter<GroupieViewHolder>.setOnSingleItemClickListener(callback: (item: Item<*>, View?) -> Unit) {
    this.setOnItemClickListener(object : SingleItemClickListener() {
        override fun performClick(item: Item<*>, view: View) {
            callback(item, view)
        }
    })
}