package com.test.androiddevelopersexample.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.enter_profile_data_fragment.*

class EnterProfileDataFragment : FragmentBase() {

    override var screenTag = "EnterProfileDataFragment"

    companion object {
        fun newInstance() =
            EnterProfileDataFragment()
    }

    val registrationViewModel by activityViewModels<RegistrationViewModel>()

    val args: EnterProfileDataFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.enter_profile_data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        val myText = args.registerText
        textViewEnterProfileData.text = myText


        // When the next button is clicked, collect the current values from the
        // two edit texts and pass to the ViewModel to store.
        button.setOnClickListener {
            val name = editTextTextPersonName.text.toString()
            val bio = editTextTextPersonName2.text.toString()
            registrationViewModel.collectProfileData(name, bio)
        }

        // RegistrationViewModel will update the registrationState to
        // COLLECT_USER_PASSWORD when ready to move to the choose username and
        // password screen.
        registrationViewModel.registrationState.observe(
            viewLifecycleOwner, Observer { state ->
                if (state == RegistrationViewModel.RegistrationState.COLLECT_USER_PASSWORD) {

                    val extras = FragmentNavigatorExtras(
                        Pair(
                            imageView1,
                            "ProfilePreviewToAccountTransition"
                        )
                    )
                    navController.navigate(R.id.action_thirdFragment_to_forthFragment, null, null, extras)
                }
            })

        // If the user presses back, cancel the user registration and pop back
        // to the login fragment. Since this ViewModel is shared at the activity
        // scope, its state must be reset so that it will be in the initial
        // state if the user comes back to register later.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            registrationViewModel.userCancelledRegistration()
            navController.popBackStack(R.id.loginFragment, false)
        }

    }

}