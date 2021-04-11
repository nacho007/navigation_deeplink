package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentNotificationDetailBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class NotificationDetailFragment : BaseFragment<FragmentNotificationDetailBinding>() {

    override var screenTag = "NotificationDetailFragment"
    override val binding by lazy { FragmentNotificationDetailBinding.inflate(layoutInflater) }

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar)
        }
    }
}