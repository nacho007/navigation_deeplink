package com.test.androiddevelopersexample.ui.fragments.home.new_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class CheckoutFragment : FragmentBase() {

    override var screenTag = "CheckoutFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_loyalty, container, false)
    }

}
