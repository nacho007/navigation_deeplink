package com.test.androiddevelopersexample.ui.fragments.home.new_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_new_card.*

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class PaymentMethodFragment : FragmentBase() {

    override var screenTag = "PaymentMethodFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_payment_method, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            val action = PaymentMethodFragmentDirections.actionPaymentMethodFragmentToCheckoutFragment(55)
            findNavController().navigate(action)
        }
    }

}
