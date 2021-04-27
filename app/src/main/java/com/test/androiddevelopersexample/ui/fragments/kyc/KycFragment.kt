package com.test.androiddevelopersexample.ui.fragments.kyc

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.databinding.FragmentKycBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/14/21.
 */
class KycFragment : BaseFragment<FragmentKycBinding>(FragmentKycBinding::inflate) {

    override var screenTag = "KycFragment"

    override var showBottomNavigation = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnReturnToCheckout.setOnClickListener {
                setFragmentResult("REQUEST_KEY", bundleOf("data" to "button clicked"))

                // Step 4. Go back to Fragment A
                findNavController().navigateUp()
            }
        }
    }
}
