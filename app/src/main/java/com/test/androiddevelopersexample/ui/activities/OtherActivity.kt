package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class OtherActivity : BaseActivity() {

    override var screenTag = "OtherActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.data = intent.data
        mainIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(mainIntent)
        finish()
        overridePendingTransition(0, 0)
    }

}
