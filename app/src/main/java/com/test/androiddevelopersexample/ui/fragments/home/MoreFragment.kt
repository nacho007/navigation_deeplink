package com.test.androiddevelopersexample.ui.fragments.home

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.HomeActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_money.*
import kotlinx.android.synthetic.main.fragment_more.*
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : FragmentBase() {

    override var screenTag = "MoreFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_generate_badge.setOnClickListener {
            (activity as HomeActivity).createBadges(R.id.newCardFragment, 2)
            (activity as HomeActivity).createBadges(R.id.loyaltyFragment, 3)
        }

        btn_generate_notification.setOnClickListener {

            val notificationId = getRandomNumber()

            val args = Bundle()
            args.putString("arg", "MyArg")

            val pendingIntent = NavDeepLinkBuilder(requireContext())
                .setGraph(R.navigation.home_navigation_graph)
                .setDestination(R.id.moneyFragment)
                .setArguments(args)
                .createPendingIntent()

            createNotification(requireContext(), "Title", "Body", pendingIntent, notificationId)
        }
    }


    private fun createNotification(
        context: Context,
        title: String,
        body: String,
        resultPendingIntent: PendingIntent?,
        notificationId: Int
    ) {
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

            builder.setContentIntent(resultPendingIntent)
            mNotificationManager.notify(notificationId, builder.build())
        }
    }

    private fun getRandomNumber(): Int {
        val random = Random()
        return random.nextInt(9999 - 1000) + 1000
    }

    companion object {
        const val CHANNEL_ID = "astropay_channel"
    }
}
