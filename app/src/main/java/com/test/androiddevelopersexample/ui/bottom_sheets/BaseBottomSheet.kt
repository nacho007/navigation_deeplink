package com.test.androiddevelopersexample.ui.bottom_sheets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.androiddevelopersexample.ui.fragments.base.Inflate

abstract class BaseBottomSheet<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
    private var initialBehaviorState: Int = BottomSheetBehavior.STATE_EXPANDED
): BottomSheetDialogFragment() {

    lateinit var dialog: BottomSheetDialog

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

            BottomSheetBehavior.from(bottomSheet).apply {
                isFitToContents = true
                peekHeight = 0
                state = initialBehaviorState
                skipCollapsed = true
                setHideable(true)
            }
        }

        return dialog
    }

    companion object {
        const val DIALOG_RESULT = "DialogResult"
    }
}