package com.test.androiddevelopersexample.ui.fragments.home

import com.test.androiddevelopersexample.databinding.FragmentMoneyBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentMoneyBinding>() {

    override var screenTag = "MoneyFragment"
    override val binding by lazy { FragmentMoneyBinding.inflate(layoutInflater) }

    override var showBottomNavigation = true

}
