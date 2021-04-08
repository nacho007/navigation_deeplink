package com.test.androiddevelopersexample.ui.fragments.home.new_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.DeepLink
import com.test.androiddevelopersexample.ui.activities.PaymentMethodsActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_new_card.*

class NewCardFragment : FragmentBase() {

    override var screenTag = "NewCardFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_new_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            startActivity(PaymentMethodsActivity.getCallingIntent(requireActivity(), DeepLink.CheckOut(22)))
        }
    }

}