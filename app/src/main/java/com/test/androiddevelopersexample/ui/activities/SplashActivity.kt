package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.androiddevelopersexample.R


/**
 * Created by ignaciodeandreisdenis on 10/15/20.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    fun navigateToHome() {
        Intent(this, HomeActivity::class.java).apply {
            this.data = intent.data
            this.putExtras(extras ?: Bundle())
            this.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(this)
            finish()
            overridePendingTransition(0, 0)
        }
    }
}
