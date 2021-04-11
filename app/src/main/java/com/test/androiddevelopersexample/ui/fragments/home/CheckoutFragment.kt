package com.test.androiddevelopersexample.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentCheckoutBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>() {

    override var screenTag = "CheckoutFragment"
    override val binding by lazy { FragmentCheckoutBinding.inflate(layoutInflater) }

    override var showBottomNavigation = false

    private val args: CheckoutFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.checkoutFragment)
            tvId.text = "Id: " + args.id
        }
    }

}
