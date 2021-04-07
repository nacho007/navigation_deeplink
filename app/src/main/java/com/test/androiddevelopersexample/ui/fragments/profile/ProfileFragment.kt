package com.test.androiddevelopersexample.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.fragments.login.LoginViewModel

class ProfileFragment : FragmentBase() {

    override var screenTag = "ProfileFragment"

    companion object {
        fun newInstance() =
            ProfileFragment()
    }

    private val viewModel: LoginViewModel by activityViewModels()
    private lateinit var welcomeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeTextView = view.findViewById(R.id.textViewWelcome)

        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    navController.navigate(
                        R.id.loginFragment
                    )
                }
                else -> Log.e("", "")
            }
        })

    }


    private fun showWelcomeMessage() {
        welcomeTextView.text = getString(R.string.welcome, viewModel.username)
    }

}