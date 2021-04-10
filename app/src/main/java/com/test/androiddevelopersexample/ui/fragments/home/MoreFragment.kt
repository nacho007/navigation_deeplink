package com.test.androiddevelopersexample.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.fragment_more.*

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : FragmentBase() {

    override var screenTag = "MoreFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_generate_badge.setOnClickListener {
            (activity as MainActivity).createBadges(R.id.newCardFragment, 2)
            (activity as MainActivity).createBadges(R.id.loyaltyFragment, 3)
        }

        btn_logout.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), false)?.apply()

            if (activity?.intent?.data != null) {
                findNavController().navigate(R.id.action_moreFragment_to_mainFragment2)
            } else {
                findNavController().navigate(R.id.action_moreFragment_to_mainFragment)
            }
        }
    }

}
