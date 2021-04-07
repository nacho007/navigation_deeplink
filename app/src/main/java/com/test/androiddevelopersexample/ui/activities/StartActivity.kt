package com.test.androiddevelopersexample.ui.activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.androiddevelopersexample.R
import kotlinx.android.synthetic.main.activity_main_navigation.*

/**
 * Created by ignaciodeandreisdenis on 7/22/20.
 */
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        val navController = findNavController(R.id.fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        var badge = bottom_nav.getOrCreateBadge(R.id.details_page_fragment_2)
        badge.isVisible = true
// An icon only badge will be displayed unless a number is set:
        badge.number = 99

    }
}
