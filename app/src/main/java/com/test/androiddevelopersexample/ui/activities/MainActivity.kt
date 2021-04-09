package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav.visibility = GONE

        val navController = findNavController(R.id.fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newCardFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_new_card))
                }
                R.id.loyaltyFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_loyalty))
                }
                R.id.moneyFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_money))
                }
                R.id.notificationsFragment -> {
                    Log.e(
                        "BottomNavigation",
                        getString(R.string.tab_notifications)
                    )
                }
                R.id.moreFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_more))
                }
            }
        }

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

        if (intent?.data != null) {

            val request = NavDeepLinkRequest.Builder
                .fromUri("app://loyalty".toUri())
                .build()

            findNavController(R.id.fragment).navigate(request)

//            if (intent.data.toString().contains("app://payment-methods")) {

//                val args = Bundle()
//                args.putString("arg", "app:loyalty")

//                val pendingIntent = NavDeepLinkBuilder(this)
//                    .setComponentName(MainActivity::class.java)
//                    .setGraph(R.navigation.home_navigation_graph)
//                    .setDestination(R.id.activityPaymentMethods)
//                    .setArguments(args)
//                    .createPendingIntent()
//                pendingIntent.send()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.data != null) {

            val request = NavDeepLinkRequest.Builder
                .fromUri("app://loyalty".toUri())
                .build()

            findNavController(R.id.fragment).navigate(request)
            if (intent.data.toString().contains("app://payment-methods")) {

                val args = Bundle()
                args.putString("arg", "MyArg")

//                val pendingIntent = NavDeepLinkBuilder(this)
//                    .setComponentName(HomeActivity::class.java)
//                    .setGraph(R.navigation.home_navigation_graph)
//                    .setDestination(R.id.activityPaymentMethods)
//                    .setArguments(args)
//                    .createPendingIntent()
//                pendingIntent.send()
            }
        }
    }
}
