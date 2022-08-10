package com.test.androiddevelopersexample.ui.fragments.notification_center

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentNotificationDetailBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class NotificationDetailFragment : BaseFragment<FragmentNotificationDetailBinding>(FragmentNotificationDetailBinding::inflate) {

    override var screenTag = "NotificationDetailFragment"

    override var showBottomNavigation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.notificationsFragment)

            btnDeepLink.setOnClickListener {
                try {
                    findNavController().navigate(DeepLinkUtils.deepLinkLoyalty())
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }

    override val fragmentName: String
        get() = "NotificationDetailFragment"
    override val screenName: String
        get() = "NotificationDetailFragment"
}