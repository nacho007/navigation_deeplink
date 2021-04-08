package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import kotlinx.android.synthetic.main.activity_home.*
import java.io.Serializable


/**
 * Created by ignaciodeandreisdenis on 7/22/20.
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navController = findNavController(R.id.fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newCardFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_new_card))
                    createBadges(id = R.id.newCardFragment, quantity = 0, visible = false)
                }
                R.id.loyaltyFragment -> {
                    Log.e("BottomNavigation", getString(R.string.tab_loyalty))
                    createBadges(id = R.id.loyaltyFragment, quantity = 0, visible = false)
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

        processDeepLink(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("ds", "sd")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processDeepLink(intent)
    }

    private fun processDeepLink(intent: Intent?) {
        if (intent?.data != null) {
            if (intent.data.toString().contains("app://payment-methods")) {

                val args = Bundle()
                args.putString("arg", "MyArg")

                val pendingIntent = NavDeepLinkBuilder(this)
                    .setGraph(R.navigation.home_navigation_graph)
                    .setDestination(R.id.activityPaymentMethods)
                    .setArguments(args)
                    .createPendingIntent()

                val intentSender = pendingIntent.intentSender
                startIntentSenderForResult(intentSender, 1, null, 0, 0, 0)
            }
        }
    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = bottom_nav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_2)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }

    companion object {
        const val DEEP_LINK = "deep_link"
    }

    sealed class DeepLink : Serializable {
        data class CheckOut(val id: Int) : DeepLink()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

}
