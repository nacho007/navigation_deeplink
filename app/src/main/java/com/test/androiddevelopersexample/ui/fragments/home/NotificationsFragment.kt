package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentNotificationsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override var screenTag = "NotificationsFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnDeepLink.setOnClickListener {
                try {
                    findNavController().navigate(DeepLinkUtils.deepLinkLoyalty())
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }

            btnNavigateToDetail.setOnClickListener {
                findNavController().navigate(R.id.action_notificationsFragment_to_notificationDetailFragment)
            }
        }

    }
}
