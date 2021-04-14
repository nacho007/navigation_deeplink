package com.test.androiddevelopersexample.ui.fragments.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.test.androiddevelopersexample.databinding.FragmentMainBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import java.util.concurrent.Executor

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override var screenTag = "MainFragment"
    override val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val executor: Executor = ContextCompat.getMainExecutor(requireContext())
        executor.execute {
            (activity as MainActivity).process()
        }

//        val handler = Handler()
//        handler.postDelayed(Runnable { (activity as MainActivity).process() }, 2000)
    }
}
