package com.test.androiddevelopersexample.ui.fragments.home

import com.test.androiddevelopersexample.databinding.FragmentLoyaltyBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class LoyaltyFragment : BaseFragment<FragmentLoyaltyBinding>() {

    override var screenTag = "LoyaltyFragment"
    override val binding by lazy { FragmentLoyaltyBinding.inflate(layoutInflater) }

    override var showBottomNavigation = true

}
