package com.test.androiddevelopersexample.services

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.FIREBASE_TOKEN
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_LOYALTY
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_TYPE
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils

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

        getSharedPreferences(
            getString(R.string.preferences), Context.MODE_PRIVATE
        )?.edit()?.putString(FIREBASE_TOKEN, s)?.apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        processNotificationPushWithData(remoteMessage.notification, remoteMessage.data)
    }

    private fun processNotificationPushWithData(
        notification: RemoteMessage.Notification?,
        data: Map<String, String>
    ) {
        try {
            var type: String? = ""

            for (key in data.keys) {
                if (key == PUSH_TYPE) {
                    type = data[key]
                }
            }

            when (type) {
                PUSH_LOYALTY -> {
                    PushNotificationUtils.createTradePushLoyalty(
                        this,
                        notification?.title ?: getString(R.string.app_name),
                        notification?.body ?: ""
                    )
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
