package com.test.androiddevelopersexample.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.ActivityMainBinding
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import java.lang.Exception


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override var screenTag = "MainActivity"

    var showBottomNavigation: Boolean = false
    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var deepLink = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showBottomNavigationMenu(showBottomNavigation)

//        val navController = findNavController(R.id.fragmentNavHost)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment).navController

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
            deepLink = true
        }

        binding.bottomNav.setupWithNavController(navController)
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
        } else {
            NavGraphHelper.setGraph(
                this,
                R.navigation.login_navigation_graph
            )
        }
    }

    fun showBottomNavigationMenu(show: Boolean) {
        showBottomNavigation = show
//        try {
            binding.bottomNav.visibility = VISIBLE.takeIf { show } ?: GONE
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }

    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = binding.bottomNav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_2)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }
}
