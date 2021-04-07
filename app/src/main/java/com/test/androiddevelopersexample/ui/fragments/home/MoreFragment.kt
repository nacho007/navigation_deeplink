package com.test.androiddevelopersexample.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.HomeActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_money.*

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
        return inflater.inflate(R.layout.fragment_money, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btb_generate_badge.setOnClickListener {
            (activity as HomeActivity).createBadges(R.id.newCardFragment, 2)
            (activity as HomeActivity).createBadges(R.id.loyaltyFragment, 3)
        }

    }
}
