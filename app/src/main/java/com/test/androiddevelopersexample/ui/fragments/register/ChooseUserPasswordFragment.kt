package com.test.androiddevelopersexample.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.fragments.login.LoginViewModel
import kotlinx.android.synthetic.main.choose_user_password_fragment.*

class ChooseUserPasswordFragment : FragmentBase() {

    override var screenTag = "ChooseUserPasswordFragment"

    companion object {
        fun newInstance() =
            ChooseUserPasswordFragment()
    }

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val registrationViewModel: RegistrationViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_user_password_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        // When the register button is clicked, collect the current values from
        // the two edit texts and pass to the ViewModel to complete registration.
        button12.setOnClickListener {
            registrationViewModel.createAccountAndLogin(
                editTextTextPersonName.text.toString(),
                editTextTextPersonName22.text.toString()
            )
        }

        // RegistrationViewModel updates the registrationState to
        // REGISTRATION_COMPLETED when ready, and for this example, the username
        // is accessed as a read-only property from RegistrationViewModel and is
        // used to directly authenticate with loginViewModel.
        registrationViewModel.registrationState.observe(
            viewLifecycleOwner, Observer { state ->
                if (state == RegistrationViewModel.RegistrationState.REGISTRATION_COMPLETED) {

                    // Here we authenticate with the token provided by the ViewModel
                    // then pop back to the profie_fragment, where the user authentication
                    // status will be tested and should be authenticated.
                    val authToken = registrationViewModel.authToken
                    loginViewModel.authenticate(authToken, editTextTextPersonName22.text.toString())
                    navController.popBackStack(R.id.profileFragment, false)
                }
            }
        )

//        // If the user presses back, cancel the user registration and pop back
//        // to the login fragment. Since this ViewModel is shared at the activity
//        // scope, its state must be reset so that it is in the initial state if
//        // the user comes back to register later.
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            registrationViewModel.userCancelledRegistration()
//            navController.popBackStack(R.id.loginFragment, false)
//        }

    }

}