package com.test.androiddevelopersexample.ui.fragments.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMainBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import com.test.androiddevelopersexample.ui.utils.navigate
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class InitFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override var screenTag = "InitFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                val sharedPref = activity?.getSharedPreferences(
                    getString(R.string.preferences), Context.MODE_PRIVATE
                )

                if (sharedPref?.getBoolean(getString(R.string.is_logged), false) == true) {
                    (activity as NavigationActivity).deepLinkUnLogged = false

                    navigate(R.id.action_initFragment_to_moneyFragment)
                    findNavController().graph.setStartDestination(R.id.moneyFragment)
                    checkDeepLinks()
                } else {
                    (activity as NavigationActivity).deepLinkLogged = false
                    navigate(R.id.action_initFragment_to_loginFragment)
                }

            }, 1500
        )
    }

    private fun checkDeepLinks() {
        when {
            //PUSH NOTIFICATION IN BACKGROUND
            (activity as NavigationActivity).pushData -> {
                DeepLinkUtils.processPushData(
                    activity?.intent?.extras
                        ?: Bundle()
                ).let { navDeepLinkRequest ->
                    if (navDeepLinkRequest != null) {
                        findNavController().navigate(navDeepLinkRequest)
                    }
                }
            }
            else -> {
                activity?.intent?.data = null
                activity?.intent?.replaceExtras(null)
            }
        }
    }
}
