package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentNewCardBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {

    override var screenTag = "NewCardFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewCard.setOnClickListener {
            findNavController().navigate(R.id.action_newCardFragment_to_paymentMethodFragment)
        }
    }
}