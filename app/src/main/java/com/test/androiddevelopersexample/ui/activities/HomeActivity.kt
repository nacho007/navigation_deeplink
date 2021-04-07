package com.test.androiddevelopersexample.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import kotlinx.android.synthetic.main.activity_home.*

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
    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = bottom_nav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_2)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }
}
