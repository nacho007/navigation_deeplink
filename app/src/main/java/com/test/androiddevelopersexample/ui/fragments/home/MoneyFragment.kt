package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMoneyBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentMoneyBinding>() {

    override var screenTag = "MoneyFragment"

    override val binding by lazy { FragmentMoneyBinding.inflate(layoutInflater) }
    private lateinit var binding2: FragmentMoneyBinding


    override var showBottomNavigation = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding2 = FragmentMoneyBinding.inflate(inflater, container, false)
        return binding2.root
    }

}
