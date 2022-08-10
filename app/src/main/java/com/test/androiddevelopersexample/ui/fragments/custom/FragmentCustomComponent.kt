package com.test.androiddevelopersexample.ui.fragments.custom

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.databinding.FragmentCustomComponentBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 23/2/22.
 */
class FragmentCustomComponent :
    BaseFragment<FragmentCustomComponentBinding>(FragmentCustomComponentBinding::inflate) {

    override var screenTag = "FragmentCustomComponent"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            plPhone.setIcon("https://getapp-test.astropaycard.com/img/flags/AR")
        }
    }

    override val fragmentName: String
        get() = "FragmentCustomComponent"
    override val screenName: String
        get() = "FragmentCustomComponent"
}
