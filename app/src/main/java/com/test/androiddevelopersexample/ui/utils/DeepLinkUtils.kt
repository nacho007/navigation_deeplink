package com.test.androiddevelopersexample.ui.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkRequest
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import java.util.*

object DeepLinkUtils {

    const val CHANNEL_ID = "astropay_channel"

    fun createNotification(
        context: Context,
        title: String,
        body: String
    ) {
        val notificationId = getRandomNumber()

        val args = Bundle()
        args.putString("arg", "MyArg")

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_navigation_graph)
            .setDestination(R.id.loyaltyFragment)
            .setArguments(args)
            .createPendingIntent()

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                NotificationChannel(CHANNEL_ID, context.getString(R.string.app_name), importance)

            val notification = Notification.Builder(context, CHANNEL_ID)
                .setStyle(Notification.BigTextStyle())
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.astropay_icon_notification)
                .setChannelId(CHANNEL_ID)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.color_2))
                .build()

            mNotificationManager.createNotificationChannel(mChannel)
            mNotificationManager.notify(notificationId, notification)
        } else {
            val builder = NotificationCompat.Builder(context)

            builder.setAutoCancel(true)
            builder.setSmallIcon(R.mipmap.astropay_icon_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body)).color =
                ContextCompat.getColor(context, R.color.color_2)

            builder.setContentIntent(pendingIntent)
            mNotificationManager.notify(notificationId, builder.build())
        }
    }

    private fun getRandomNumber(): Int {
        val random = Random()
        return random.nextInt(9999 - 1000) + 1000
    }

    fun createDeepLink(deepLink: String): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(deepLink.toUri())
            .build()
    }

    fun deepLinkLoyalty(): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri("app://loyalty".toUri())
            .build()
    }
}
