package com.test.androiddevelopersexample.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected abstract val binding: T
    open var screenTag = "BaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(screenTag, "onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.data != null) {
            Log.e(screenTag, "onNewIntent ${intent.data.toString()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(screenTag, "onDestroy")
    }
}
