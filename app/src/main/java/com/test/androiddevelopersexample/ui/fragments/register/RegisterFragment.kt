package com.test.androiddevelopersexample.ui.fragments.register

import android.content.Context
import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentRegisterBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override var screenTag = "RegisterFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            (activity as NavigationActivity).showBottomNavigationMenu(true)

            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), true)?.apply()

        }
    }
}

