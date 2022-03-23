package com.test.androiddevelopersexample.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.ActivityNavigationBinding
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils.PUSH_TYPE


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {

    override var screenTag = "MainActivity"

    var showBottomNavigation: Boolean = false
    override val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var navHostFragment: NavHostFragment
    var deepLink = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showBottomNavigationMenu(showBottomNavigation)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentNavHost) as NavHostFragment

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
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

        if (intent?.extras != null) {
            val pushType = intent?.extras?.get(PUSH_TYPE)
            Log.e("Data create", "push $pushType")
        }

        binding.bottomNav.setupWithNavController(navHostFragment.navController)
    }

    fun showBottomNavigationMenu(show: Boolean) {
        showBottomNavigation = show
        binding.bottomNav.visibility = VISIBLE.takeIf { show } ?: GONE
    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = binding.bottomNav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_red)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }

    fun logOut() {
        setBadges(R.id.notificationsFragment, 0)
        setBadges(R.id.moreFragment, 0)
        intent?.data = null
        intent?.replaceExtras(null)
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.home_navigation, true)
            .build()
        navHostFragment.findNavController().navigate(R.id.initFragment, null, navOptions)
    }

    fun setBadges(id: Int, quantity: Int) {
        val badge = binding.bottomNav.getOrCreateBadge(id)
        badge.isVisible = quantity > 0
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_red)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }

    companion object {
        const val START_DESTINATION = "start_destination"
    }
}
