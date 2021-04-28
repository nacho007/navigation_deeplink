package com.test.androiddevelopersexample.ui.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.ActivityMainBinding
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper


/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override var screenTag = "MainActivity"

    var showBottomNavigation: Boolean = false
    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var deepLink = false

    var graphId: Int = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("graphId", graphId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNav2.visibility = GONE
        binding.bottomNav2.selectedItemId = R.id.auxFragment

        showBottomNavigationMenu(showBottomNavigation)

        val myNavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentNavHost) as NavHostFragment

        val inflater = myNavHostFragment.navController.navInflater

        graphId = savedInstanceState?.getInt("graphId") ?: 0
        val currentGraph: NavGraph

        if (graphId != 0) {
            currentGraph = inflater.inflate(graphId)
        } else {
            currentGraph = inflater.inflate(R.navigation.main_navigation_graph)
            graphId = R.navigation.main_navigation_graph
        }

        myNavHostFragment.navController.graph = currentGraph

        myNavHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newCardFragment -> {
                    Log.e("Menu", getString(R.string.tab_new_card))
                }
                R.id.loyaltyFragment -> {
                    Log.e("Menu", getString(R.string.tab_loyalty))
                }
                R.id.walletFragment -> {
                    Log.e("Menu", getString(R.string.tab_money))
                }
                R.id.cardsFragment -> {
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

    fun setMenu(fragment: Int, fragmentToRemove: Int? = null) {
        if (fragmentToRemove == null) {
            addMenu(fragment, fragmentToRemove)
        } else {
            binding.bottomNav2.visibility = VISIBLE
            binding.bottomNav.visibility = GONE

            Handler().postDelayed({
                addMenu(fragment, fragmentToRemove)
                binding.bottomNav.selectedItemId = fragment
            }, 100)

            Handler().postDelayed({
                binding.bottomNav2.visibility = GONE
                binding.bottomNav.visibility = VISIBLE
            }, 200)
        }
    }

    fun addMenu(fragment: Int, fragmentToRemove: Int? = null) {
        binding.bottomNav.apply {

            fragmentToRemove?.let {
                menu.removeItem(it)
            }

            menu.add(Menu.NONE, fragment, 30, R.string.tab_money).setIcon(R.drawable.svg_money)
        }
    }

    fun process() {
        val sharedPref = getSharedPreferences(
            getString(R.string.preferences), Context.MODE_PRIVATE
        )

        val isLogged = sharedPref?.getBoolean(getString(R.string.is_logged), false)

        if (isLogged == true) {
            graphId = R.navigation.home_navigation_graph
            NavGraphHelper.setGraph(
                this,
                R.navigation.home_navigation_graph
            )
        } else {
            graphId = R.navigation.login_navigation_graph
            NavGraphHelper.setGraph(
                this,
                R.navigation.login_navigation_graph
            )
        }
    }

    fun showBottomNavigationMenu(show: Boolean) {
        showBottomNavigation = show
        binding.bottomNav.visibility = VISIBLE.takeIf { show } ?: GONE
    }

    fun createBadges(id: Int, quantity: Int, visible: Boolean = true) {
        val badge = binding.bottomNav.getOrCreateBadge(id)
        badge.isVisible = visible
        badge.backgroundColor = ContextCompat.getColor(this, R.color.color_2)
        badge.badgeTextColor = ContextCompat.getColor(this, R.color.color_white)
        badge.number = quantity
    }
}
