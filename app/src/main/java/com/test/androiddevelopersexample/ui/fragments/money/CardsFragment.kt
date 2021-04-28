package com.test.androiddevelopersexample.ui.fragments.money

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentCardsBinding
import com.test.androiddevelopersexample.databinding.FragmentWalletBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/27/21.
 */
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCards.setOnClickListener {
            (activity as MainActivity).setMenu(R.id.walletFragment, R.id.cardsFragment)
            findNavController().navigate(R.id.action_cardsFragment_to_walletFragment)
        }
    }
}

