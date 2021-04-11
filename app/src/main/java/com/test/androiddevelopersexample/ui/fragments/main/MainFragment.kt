package com.test.androiddevelopersexample.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import java.util.concurrent.Executor

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class MainFragment : FragmentBase() {

    override var screenTag = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val executor: Executor = ContextCompat.getMainExecutor(requireContext())
        executor.execute {
            (activity as MainActivity).process()
        }
    }
}
