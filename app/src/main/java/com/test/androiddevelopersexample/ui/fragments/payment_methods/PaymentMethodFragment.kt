package com.test.androiddevelopersexample.ui.fragments.payment_methods

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentPaymentMethodBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding>(FragmentPaymentMethodBinding::inflate) {

    override var screenTag = "PaymentMethodFragment"

    override var showBottomNavigation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.paymentMethodFragment)
            btnPaymentMethods.setOnClickListener {
//                val action =
//                    PaymentMethodFragmentDirections.actionPaymentMethodFragmentToCheckoutFragment(55)
//                findNavController().navigate(action)
            }
        }
    }

    override val fragmentName: String
        get() = "PaymentMethodsFragment"
    override val screenName: String
        get() = "PaymentMethodsFragment"

}
