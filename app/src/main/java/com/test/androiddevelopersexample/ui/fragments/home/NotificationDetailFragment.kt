package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.databinding.FragmentNotificationDetailBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class NotificationDetailFragment : BaseFragment<FragmentNotificationDetailBinding>() {

    override var screenTag = "NotificationDetailFragment"
    override val binding by lazy { FragmentNotificationDetailBinding.inflate(layoutInflater) }

    override var showBottomNavigation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar)

            tvNotifications.setOnClickListener {
                findNavController().navigate(DeepLinkUtils.deepLinkLoyalty())
            }
        }
    }
}