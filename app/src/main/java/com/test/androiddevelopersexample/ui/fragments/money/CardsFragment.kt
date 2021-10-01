package com.test.androiddevelopersexample.ui.fragments.money

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentCardsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/27/21.
 */
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

