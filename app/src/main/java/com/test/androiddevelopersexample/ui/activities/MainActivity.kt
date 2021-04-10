package com.test.androiddevelopersexample.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : AppCompatActivity() {

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

        //Setup the navGraph for this activity
        val myNavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment

        val inflater = myNavHostFragment.navController.navInflater


        val sharedPref = getSharedPreferences(
            getString(R.string.preferences), Context.MODE_PRIVATE
        )

        val isLogged = sharedPref?.getBoolean(getString(R.string.is_logged), false)

        if (intent?.data != null || isLogged == true) {
            Log.e("Data create", "Data ${intent.data.toString()}")
            val graph = inflater.inflate(R.navigation.home_navigation_graph)
            navController.graph = graph
        } else {
            val graph = inflater.inflate(R.navigation.login_navigation_graph)
            navController.graph = graph
        }

        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)
    }

    fun showBottomNavigationMenu(show: Boolean) {
        showBottomNavigation = show
        bottom_nav?.visibility = VISIBLE.takeIf { show } ?: GONE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.data != null) {
            Log.e("Data new intent", "Data ${intent.data.toString()}")
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
