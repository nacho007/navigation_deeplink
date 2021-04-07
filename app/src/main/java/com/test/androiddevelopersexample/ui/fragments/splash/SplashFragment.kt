package com.test.androiddevelopersexample.ui.fragments.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.ActivityOther
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : FragmentBase() {

    override var screenTag = "SplashFragment"

    companion object {
        fun newInstance() =
            SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        buttonViewProfile?.setOnClickListener {
            val destination = ActivityNavigator(requireContext()).createDestination()
                .setIntent(Intent(requireContext(), ActivityOther::class.java))
            ActivityNavigator(requireContext()).navigate(destination, null, null, null)
        }

        button3?.setOnClickListener {
            val deepLink = "apcmobile://profile".toUri()
            findNavController().navigate(deepLink)

//            val args = Bundle()
//            args.putString("myarg", "From Widget")
//
//            val pendingIntent = NavDeepLinkBuilder(requireContext())
//                .setGraph(R.navigation.nav_graph)
//                .setDestination(R.id.activityOther2)
//                .setArguments(args)
//                .createPendingIntent()
//
//            pendingIntent.send()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}