package com.test.androiddevelopersexample.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class LoginFragment : FragmentBase() {

    override var screenTag = "LoginFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            NavGraphHelper.setGraph(requireActivity(), R.navigation.home_navigation_graph)
        }

        btn_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btn_generate_notification.setOnClickListener {
            DeepLinkUtils.createNotification(requireContext(), "Title", "Body")
        }

    }
}
