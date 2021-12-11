package com.test.androiddevelopersexample.ui.fragments.money

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMoneyBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.navigate

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentMoneyBinding>(FragmentMoneyBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override var showBottomNavigation: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnViewpager.setOnClickListener {
                navigate(R.id.action_moneyFragment_to_fragmentViewPager)
            }
        }

        binding.textView2.setOnClickListener {

        }
    }

}
