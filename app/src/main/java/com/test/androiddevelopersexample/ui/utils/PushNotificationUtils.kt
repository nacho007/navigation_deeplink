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
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_ARTICLE
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_LOYALTY
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_TYPE

object PushNotificationUtils {

    private const val CHANNEL_ID = "astropay_channel"
    const val IS_PUSH = "is_push"

    fun createTradePushArticle(context: Context, title: String, body: String) {
        val args = Bundle()
        args.putString(PUSH_TYPE, PUSH_ARTICLE)

        val pendingIntent = createPendingIntent(
            context,
            R.navigation.navigation_home,
            R.id.articleFragment,
            args
        )
        createNotification(context, title, body, pendingIntent)
    }

    fun createTradePushLoyalty(context: Context, title: String, body: String) {
        val args = Bundle()
        args.putString(PUSH_TYPE, PUSH_LOYALTY)

        val pendingIntent = createPendingIntent(
            context,
            R.navigation.navigation_home,
            R.id.loyaltyFragment,
            args
        )
        createNotification(context, title, body, pendingIntent)
    }

    private fun createPendingIntent(
        context: Context,
        graph: Int,
        destination: Int,
        args: Bundle? = null
    ): PendingIntent {
//        return NavDeepLinkBuilder(context)
//            .setComponentName(NavigationActivity::class.java)
//            .setGraph(graph)
//            .addDestination(R.id.initFragment, bundleOf(IS_PUSH to true))
//            .addDestination(destination)
//            .setArguments(args)
//            .createPendingIntent()
        return NavDeepLinkBuilder(context)
            .setComponentName(NavigationActivity::class.java)
            .setGraph(graph)
            .setDestination(destination, bundleOf(IS_PUSH to true))
            .setArguments(args)
            .createPendingIntent()
    }

    private fun createNotification(
        context: Context,
        title: String,
        body: String,
        resultPendingIntent: PendingIntent?
    ) {
        val notificationId = Utils.getRandomNumber()

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
                .setContentIntent(resultPendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.color_red))
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
                ContextCompat.getColor(context, R.color.color_red)

            builder.setContentIntent(resultPendingIntent)
            mNotificationManager.notify(notificationId, builder.build())
        }
    }
}