package com.test.androiddevelopersexample.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.test.androiddevelopersexample.R

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class AstroCoinsActivity : BaseActivity() {

    override var screenTag = "AstroCoinsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astro_coins)
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
