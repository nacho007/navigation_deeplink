package com.test.androiddevelopersexample.ui.fragments.astro_coins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class AstroCoinsFragment : FragmentBase() {

    override var screenTag = "AstroCoinsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showBottomNavigationMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_astro_coins, container, false)
    }
}
