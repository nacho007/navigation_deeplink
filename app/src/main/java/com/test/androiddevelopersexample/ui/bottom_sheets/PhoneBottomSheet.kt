package com.test.androiddevelopersexample.ui.bottom_sheets

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.androiddevelopersexample.databinding.BottomSheetPhoneBinding

class PhoneBottomSheet : BaseBottomSheet<BottomSheetPhoneBinding>(
    BottomSheetPhoneBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBs.setOnClickListener {
                when (dialog.behavior.state) {
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }
                    else -> {
                        // Nothing to do here
                    }
                }
            }
        }
    }
}