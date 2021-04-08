package com.test.androiddevelopersexample.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.HomeActivity.Companion.DEEP_LINK

/**
 * Created by ignaciodeandreisdenis on 7/22/20.
 */
class PaymentMethodsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_methods)

        val deepLink = intent.getSerializableExtra(DEEP_LINK) as HomeActivity.DeepLink?
        deepLink?.let {
            val args = Bundle().apply {
                putInt("id", (deepLink as HomeActivity.DeepLink.CheckOut).id)
            }
            findNavController(R.id.fragment).navigate(R.id.checkoutFragment, args)
        }
    }

    companion object {
        fun getCallingIntent(context: Context, deepLink: HomeActivity.DeepLink? ): Intent {
            return Intent(context, PaymentMethodsActivity::class.java).apply {
                deepLink?.let {
                    putExtra(DEEP_LINK, deepLink)
                }
            }
        }
    }
}
