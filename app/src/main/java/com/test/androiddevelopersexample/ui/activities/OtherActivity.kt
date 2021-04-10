package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("onCreate", "OtherActivity")

        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.data = intent.data
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(mainIntent)
        finish()
        overridePendingTransition(0, 0)
    }
}
