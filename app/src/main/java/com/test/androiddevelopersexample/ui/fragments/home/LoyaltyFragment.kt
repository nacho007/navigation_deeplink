package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_loyalty.*

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class LoyaltyFragment : FragmentBase() {

    override var screenTag = "LoyaltyFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_loyalty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_loyalty.setOnClickListener {
            val action = LoyaltyFragmentDirections.actionLoyaltyFragmentToCheckoutFragment2(55)
            findNavController().navigate(action)
        }
    }
}
