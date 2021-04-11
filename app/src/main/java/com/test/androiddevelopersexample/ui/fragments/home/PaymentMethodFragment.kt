package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentPaymentMethodBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class PaymentMethodFragment : BaseFragment<FragmentPaymentMethodBinding>() {

    override var screenTag = "PaymentMethodFragment"
    override val binding by lazy { FragmentPaymentMethodBinding.inflate(layoutInflater) }

    override var showBottomNavigation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.paymentMethodFragment)
            btnPaymentMethods.setOnClickListener {
                val action =
                    PaymentMethodFragmentDirections.actionPaymentMethodFragmentToCheckoutFragment(55)
                findNavController().navigate(action)
            }
        }
    }

}
