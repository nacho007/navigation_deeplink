package com.test.androiddevelopersexample.ui.fragments.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.AstroCoinsActivity
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import kotlinx.android.synthetic.main.fragment_more.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : FragmentBase() {

    override var screenTag = "MoreFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_generate_badge.setOnClickListener {
            (activity as MainActivity).createBadges(R.id.newCardFragment, 2)
            (activity as MainActivity).createBadges(R.id.loyaltyFragment, 3)
        }

        btn_astro_coins.setOnClickListener {
            val intent = Intent(activity, AstroCoinsActivity::class.java)
            startActivityForResult(intent, 666)
        }

        btn_generate_notifications.setOnClickListener {
            val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            executor.schedule({
                DeepLinkUtils.createNotification(requireContext(), "Title", "Body")
            }, 2000.toLong(), TimeUnit.MILLISECONDS)
        }

        btn_logout.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), false)?.apply()

            (activity as MainActivity).showBottomNavigationMenu(false)
            NavGraphHelper.setGraph(
                requireActivity(),
                R.navigation.login_navigation_graph
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 666) {
            Log.e(screenTag, "onActivityResult")
        }
    }

}
