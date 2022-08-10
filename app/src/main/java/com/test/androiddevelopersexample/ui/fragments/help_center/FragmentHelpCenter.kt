package com.test.androiddevelopersexample.ui.fragments.help_center

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentHelpCenterBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.navigate

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class FragmentHelpCenter :
    BaseFragment<FragmentHelpCenterBinding>(FragmentHelpCenterBinding::inflate) {

    override var screenTag = "FragmentHelpCenter"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.fragmentHelpCenter)
        }

        binding.btnGoToArticle.setOnClickListener {
            navigate(R.id.action_fragmentHelpCenter_to_articleFragment)
        }
    }

    override val fragmentName: String
        get() = "HelpCenterFragment"
    override val screenName: String
        get() = "HelpCenterFragment"

}
