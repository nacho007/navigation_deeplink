package com.test.androiddevelopersexample.ui.utils

import android.os.SystemClock
import android.view.View
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
abstract class SingleItemClickListener : OnItemClickListener {

    private var defaultInterval = 1000
    private var lastTimeClicked: Long = 0

    override fun onItemClick(item: Item<*>, view: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        performClick(item, view)
    }

    open fun performClick(item: Item<*>, view: View) {}
}
