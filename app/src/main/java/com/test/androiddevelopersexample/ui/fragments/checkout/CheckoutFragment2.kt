package com.test.androiddevelopersexample.ui.fragments.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentCheckout2Binding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class CheckoutFragment2 : BaseFragment<FragmentCheckout2Binding>(FragmentCheckout2Binding::inflate) {

    override var screenTag = "CheckoutFragment2"

    override var showBottomNavigation = false


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textView4.setOnClickListener {

            }
        }
    }

    override val fragmentName: String
        get() = "CheckoutFragment2"
    override val screenName: String
        get() = "CheckoutFragment2"

}
