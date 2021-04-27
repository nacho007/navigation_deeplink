package com.test.androiddevelopersexample.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentLoginBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override var screenTag = "LoginFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            (activity as MainActivity).showBottomNavigationMenu(true)

            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), true)?.apply()

            NavGraphHelper.setGraph(
                requireActivity(),
                R.navigation.home_navigation_graph
            )
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnGenerateNotification.setOnClickListener {
            val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            executor.schedule({
                DeepLinkUtils.createNotification(requireContext(), "Title", "Body")
            }, 3000.toLong(), TimeUnit.MILLISECONDS)
        }

    }
}
