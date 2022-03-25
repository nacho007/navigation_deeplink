package com.test.androiddevelopersexample.ui.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.test.androiddevelopersexample.R
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

fun Fragment.showToast(text: String?, rootViewSnackBar: View?) {
    if (text?.isNotEmpty() == true && rootViewSnackBar != null) {
        val snackBar = Snackbar.make(rootViewSnackBar, text, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        val snackBarTextView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

        snackBarTextView.apply {
            maxLines = 4
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setTextColor(ContextCompat.getColor(requireContext(), R.color.color_white))
            this.text = text
        }

        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_snack_bar
            )
        )
        snackBar.show()
    }
}