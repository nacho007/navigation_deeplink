package com.test.androiddevelopersexample.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R

/**
 * Created by ignaciodeandreisdenis on 7/22/20.
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navController = findNavController(R.id.fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        // TODO remove this
//        val badge = bottom_nav.getOrCreateBadge(R.id.details_page_fragment_2)
//        badge.isVisible = true
//        badge.number = 99

    }
}
