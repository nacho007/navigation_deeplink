package com.test.androiddevelopersexample.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by ignaciodeandreisdenis on 4/8/21.
 */
class MainFragment : FragmentBase() {

    override var screenTag = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).showBottomNavigationMenu(false)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_go_home.setOnClickListener {
            (activity as MainActivity).showBottomNavigationMenu(true)
            val action = MainFragmentDirections.actionMainFragmentToHomeGraph()
            findNavController().navigate(action)
        }

        btn_generate_notification.setOnClickListener {
            DeepLinkUtils.createNotification(requireContext(), "Title", "Body")
        }
    }

}
