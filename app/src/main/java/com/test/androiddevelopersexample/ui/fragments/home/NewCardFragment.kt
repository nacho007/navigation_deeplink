package com.test.androiddevelopersexample.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase

class NewCardFragment : FragmentBase() {

    override var screenTag = "NewCardFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_new_card, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        buttonViewProfile?.setOnClickListener {
//            val destination = ActivityNavigator(requireContext()).createDestination()
//                .setIntent(Intent(requireContext(), SplashActivity::class.java))
//            ActivityNavigator(requireContext()).navigate(destination, null, null, null)
//        }
//
//        button3?.setOnClickListener {
//            val deepLink = "apcmobile://profile".toUri()
//            findNavController().navigate(deepLink)

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
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}