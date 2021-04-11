package com.test.androiddevelopersexample.ui.activities

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

}
