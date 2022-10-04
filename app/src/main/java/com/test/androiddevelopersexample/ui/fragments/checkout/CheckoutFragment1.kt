package com.test.androiddevelopersexample.ui.fragments.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentCheckout1Binding
import com.test.androiddevelopersexample.databinding.FragmentCheckoutBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.navigate

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class CheckoutFragment1 : BaseFragment<FragmentCheckout1Binding>(FragmentCheckout1Binding::inflate) {

    override var screenTag = "CheckoutFragment1"

    override var showBottomNavigation = false


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textView4.setOnClickListener {
                navigate(R.id.checkoutFragment2)
            }
        }
    }

    override val fragmentName: String
        get() = "CheckoutFragment1"
    override val screenName: String
        get() = "CheckoutFragment1"

}
