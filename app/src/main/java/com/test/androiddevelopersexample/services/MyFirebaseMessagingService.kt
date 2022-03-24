package com.test.androiddevelopersexample.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

/**
 * Created by ignaciodeandreisdenis on 24/3/22.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FirebaseMessagingService"
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e(TAG, s)
    }

}
