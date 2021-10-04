package com.test.androiddevelopersexample.ui.fragments.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMainBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.navigate
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class InitFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override var screenTag = "MainFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                val sharedPref = activity?.getSharedPreferences(
                    getString(R.string.preferences), Context.MODE_PRIVATE
                )

                if (sharedPref?.getBoolean(getString(R.string.is_logged), false) == true) {
                    navigate(R.id.action_initFragment_to_moneyFragment)
                } else {
                    navigate(R.id.action_initFragment_to_loginFragment)
                }

            }, 1500
        )
    }
}
