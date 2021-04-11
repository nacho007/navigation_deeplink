package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.databinding.FragmentNotificationsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {

    override var screenTag = "NotificationsFragment"
    override val binding by lazy { FragmentNotificationsBinding.inflate(layoutInflater) }

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNotifications.setOnClickListener {
            findNavController().navigate(DeepLinkUtils.deepLinkLoyalty())
        }
    }
}
