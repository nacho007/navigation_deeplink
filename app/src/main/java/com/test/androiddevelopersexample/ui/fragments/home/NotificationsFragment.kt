package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import kotlinx.android.synthetic.main.fragment_notifications.*

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class NotificationsFragment : FragmentBase() {

    override var screenTag = "NotificationsFragment"

    override var showBottomNavigation = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_notifications.setOnClickListener {
            findNavController().navigate(DeepLinkUtils.deepLinkLoyalty())
        }
    }
}
