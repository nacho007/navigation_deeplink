package com.test.androiddevelopersexample.ui.fragments.base

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding

/**
 * Created by ignaciodeandreisdenis on 22/2/22.
 */
abstract class BaseContactsFragment<VB : ViewBinding>(
    inflate: Inflate<VB>
) : BaseFragment<VB>(inflate) {

    abstract val onContactsPermissionGranted: () -> Unit
    abstract val onContactsPermissionDenied: () -> Unit

    private val requestContactsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onContactsPermissionGranted()
            } else {
                onContactsPermissionDenied()
                weCantContinueToast()
            }
        }

    fun requestContactsPermission() {
        when {
            checkPermissionGranted(Manifest.permission.READ_CONTACTS) -> onContactsPermissionGranted()
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {

            }
            else -> {
                requestContactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    companion object {
        private const val RATIONALE_CONTACTS_FLOW = 13
    }
}

