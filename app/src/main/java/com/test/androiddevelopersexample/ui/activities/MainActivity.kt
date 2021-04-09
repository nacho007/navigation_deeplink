package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBottomNavigationMenu(false)

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
            Log.e("Data", "Data ${intent.data.toString()}")
            showBottomNavigationMenu(true)
            findNavController(R.id.fragment).navigate(DeepLinkUtils.createDeepLink(intent.data.toString()))
        }
    }

    fun showBottomNavigationMenu(show: Boolean) {
        bottom_nav?.visibility = VISIBLE.takeIf { show } ?: GONE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.data != null) {
            Log.e("Data", "Data ${intent.data.toString()}")
            showBottomNavigationMenu(true)
            findNavController(R.id.fragment).navigate(DeepLinkUtils.createDeepLink(intent.data.toString()))
        }
    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = bottom_nav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_2)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }

}
