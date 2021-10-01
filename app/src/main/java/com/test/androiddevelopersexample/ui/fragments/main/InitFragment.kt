package com.test.androiddevelopersexample.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMainBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.navigate
import java.util.concurrent.Executor

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class InitFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override var screenTag = "MainFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val executor: Executor = ContextCompat.getMainExecutor(requireContext())
        executor.execute {
            navigate(R.id.action_initFragment_to_loginFragment)
        }
    }
}
