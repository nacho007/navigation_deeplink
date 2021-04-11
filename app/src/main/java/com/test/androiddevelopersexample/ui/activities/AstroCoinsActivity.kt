package com.test.androiddevelopersexample.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.test.androiddevelopersexample.databinding.ActivityAstroCoinsBinding

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class AstroCoinsActivity : BaseActivity<ActivityAstroCoinsBinding>() {

    override var screenTag = "AstroCoinsActivity"
    override val binding: ActivityAstroCoinsBinding by lazy { ActivityAstroCoinsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
