package com.test.androiddevelopersexample.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.ActivityNavigationBinding


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class NavigationActivity : BaseActivity<ActivityNavigationBinding>() {

    override var screenTag = "MainActivity"

    var showBottomNavigation: Boolean = false
    override val binding: ActivityNavigationBinding by lazy { ActivityNavigationBinding.inflate(layoutInflater) }
    var deepLink = false

    var graphId: Int = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("graphId", 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showBottomNavigationMenu(showBottomNavigation)

        val myNavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentNavHost) as NavHostFragment

        val inflater = myNavHostFragment.navController.navInflater

        graphId = savedInstanceState?.getInt("graphId") ?: 0
        val currentGraph: NavGraph

        if (graphId != 1) {
            currentGraph = inflater.inflate(R.navigation.navigation_home)
            myNavHostFragment.navController.graph = currentGraph
        }

        myNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
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

        binding.bottomNav.setupWithNavController(myNavHostFragment.navController)
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
}
