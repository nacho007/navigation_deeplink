package com.test.androiddevelopersexample.ui.fragments.money

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMoneyBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentMoneyBinding>(FragmentMoneyBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView2.setOnClickListener {

            val hola = false

            if (hola) {
                (activity as MainActivity).setMenu(R.id.cardsFragment)
                findNavController().navigate(R.id.action_moneyFragment_to_cardsFragment)
            } else {
                (activity as MainActivity).setMenu(R.id.walletFragment)
                findNavController().navigate(R.id.action_moneyFragment_to_walletFragment)
            }
        }
    }

}
