package com.test.androiddevelopersexample.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMoreBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {

    override var screenTag = "MoreFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerateBadge.setOnClickListener {
            (activity as NavigationActivity).createBadges(R.id.newCardFragment, 2)
            (activity as NavigationActivity).createBadges(R.id.loyaltyFragment, 3)
        }

        binding.btnAstroCoins.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_astroCoinsFragment)
        }

        binding.btnGenerateNotifications.setOnClickListener {
            val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            executor.schedule({
                DeepLinkUtils.createNotification(requireContext(), "Title", "Body")
            }, 3000.toLong(), TimeUnit.MILLISECONDS)
        }

        binding.btnLogout.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), false)?.apply()

            (activity as NavigationActivity).showBottomNavigationMenu(false)

        }
    }
}
