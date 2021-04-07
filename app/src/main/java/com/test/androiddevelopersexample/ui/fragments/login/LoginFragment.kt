package com.test.androiddevelopersexample.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : FragmentBase() {

    override var screenTag = "LoginFragment"

    companion object {
        fun newInstance() =
            LoginFragment()
    }

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginButton.setOnClickListener {
            viewModel.authenticate(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.refuseAuthentication()
            findNavController().popBackStack(R.id.splashFragment, false)
        }

        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.popBackStack()
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> showErrorMessage()
                else -> Log.v("Error", "Error")
            }
        })


        buttonRegister?.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToRegistration(
                    "Chau"
                )
            findNavController().navigate(action)
        }

    }

    private fun showErrorMessage() {
        val text = "INVALID_AUTHENTICATION"
        val duration = Toast.LENGTH_SHORT
        Toast.makeText(context, text, duration).show()
    }

}