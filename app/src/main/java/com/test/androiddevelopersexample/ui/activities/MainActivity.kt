package com.test.androiddevelopersexample.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : BaseActivity() {

    override var screenTag = "MainActivity"

    var showBottomNavigation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBottomNavigationMenu(showBottomNavigation)

        val navController = findNavController(R.id.fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newCardFragment -> {
                    Log.e("Menu", getString(R.string.tab_new_card))
                }
                R.id.loyaltyFragment -> {
                    Log.e("Menu", getString(R.string.tab_loyalty))
                }
                R.id.moneyFragment -> {
                    Log.e("Menu", getString(R.string.tab_money))
                }
                R.id.notificationsFragment -> {
                    Log.e(
                        "Menu",
                        getString(R.string.tab_notifications)
                    )
                }
                R.id.moreFragment -> {
                    Log.e("Menu", getString(R.string.tab_more))
                }
            }
        }

        if (intent?.data != null) {
            Log.e("Data create", "Data ${intent.data.toString()}")
        }

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
    }

    fun process() {
        val sharedPref = getSharedPreferences(
            getString(R.string.preferences), Context.MODE_PRIVATE
        )

        val isLogged = sharedPref?.getBoolean(getString(R.string.is_logged), false)

        if (isLogged == true) {
            NavGraphHelper.setGraph(
                this,
                R.navigation.home_navigation_graph
            )
            showBottomNavigation = true
            showBottomNavigationMenu(showBottomNavigation)
        } else {
            NavGraphHelper.setGraph(
                this,
                R.navigation.login_navigation_graph
            )
        }
    }

    fun showBottomNavigationMenu(show: Boolean) {
        showBottomNavigation = show
        bottom_nav?.visibility = VISIBLE.takeIf { show } ?: GONE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.data != null) {
            findNavController(R.id.fragment).handleDeepLink(intent)
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
