package com.test.androiddevelopersexample.ui.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.utils.NavGraphHelper
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class RegisterFragment : FragmentBase() {

    override var screenTag = "RegisterFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_register.setOnClickListener {
            NavGraphHelper.setGraph(requireActivity(), R.navigation.home_navigation_graph)
        }
    }
}

