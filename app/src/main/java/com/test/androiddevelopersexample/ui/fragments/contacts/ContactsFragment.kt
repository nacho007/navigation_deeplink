package com.test.androiddevelopersexample.ui.fragments.contacts

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentContactsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseContactsFragment

/**
 * Created by ignaciodeandreisdenis on 22/2/22.
 */
class ContactsFragment :
    BaseContactsFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate) {

    override var screenTag = "ContactsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnPermissions.setOnClickListener {
                requestContactsPermission()
            }
        }
    }

    override val onContactsPermissionGranted: () -> Unit = {

    }

    override val onContactsPermissionDenied: () -> Unit = {

    }


}
