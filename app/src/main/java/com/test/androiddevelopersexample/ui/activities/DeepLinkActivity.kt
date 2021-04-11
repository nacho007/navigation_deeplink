package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import com.test.androiddevelopersexample.databinding.ActivityDeepLinkBinding
import com.test.androiddevelopersexample.databinding.ActivityMainBinding

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class DeepLinkActivity : BaseActivity<ActivityDeepLinkBinding>() {

    override var screenTag = "DeepLinkActivity"
    override val binding: ActivityDeepLinkBinding by lazy {
        ActivityDeepLinkBinding.inflate(
            layoutInflater
        )
    }

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
