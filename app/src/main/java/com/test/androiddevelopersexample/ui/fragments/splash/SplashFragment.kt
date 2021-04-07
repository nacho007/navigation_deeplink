package com.test.androiddevelopersexample.ui.fragments.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.HomeActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 10/15/20.
 */
class SplashFragment : FragmentBase() {

    override var screenTag = "FragmentOther"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        }, 800.toLong(), TimeUnit.MILLISECONDS)
    }
}
