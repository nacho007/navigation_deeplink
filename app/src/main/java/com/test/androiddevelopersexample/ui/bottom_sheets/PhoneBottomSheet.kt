package com.test.androiddevelopersexample.ui.bottom_sheets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.BottomSheetPhoneBinding

class PhoneBottomSheet : BaseBottomSheet<BottomSheetPhoneBinding>(
    BottomSheetPhoneBinding::inflate,
    BottomSheetBehavior.STATE_HALF_EXPANDED
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            etBs.setOnFocusChangeListener { view, b ->
                if(b){
                    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

//            btnBs.setOnClickListener {
//                when (dialog.behavior.state) {
//                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
//                        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//                    }
//                    else -> {
//                        // Nothing to do here
//                    }
//                }
//            }
        }
    }
}