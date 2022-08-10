package com.test.androiddevelopersexample.ui.fragments.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentCheckoutBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    override var screenTag = "CheckoutFragment"

    override var showBottomNavigation = false

//    private val args: CheckoutFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.checkoutFragment)
//            tvId.text = "Id: " + args.id
//
//            // Step 1. Listen for fragment results
//            setFragmentResultListener("REQUEST_KEY") { key, bundle ->
//                // read from the bundle
//                Log.e(screenTag, "onResultFragment")
//            }
//
//            tvId.setOnClickListener {
//                findNavController().navigate(R.id.action_checkoutFragment_to_kyc_navigation)
//            }
        }
    }

    override val fragmentName: String
        get() = "CheckoutFragment"
    override val screenName: String
        get() = "CheckoutFragment"

}
