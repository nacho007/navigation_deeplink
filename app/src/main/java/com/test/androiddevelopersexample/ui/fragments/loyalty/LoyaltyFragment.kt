package com.test.androiddevelopersexample.ui.fragments.loyalty

import android.os.Bundle
import android.util.Log
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentLoyaltyBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class LoyaltyFragment : BaseFragment<FragmentLoyaltyBinding>(FragmentLoyaltyBinding::inflate) {

    override var screenTag = "LoyaltyFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.getBoolean(PushNotificationUtils.IS_PUSH) == true) {
            Log.e(screenTag, "1")
        } else {
            Log.e(screenTag, "2")
        }
    }

    override val fragmentName: String
        get() = "LoyaltyFragment"
    override val screenName: String
        get() = "LoyaltyFragment"

}
