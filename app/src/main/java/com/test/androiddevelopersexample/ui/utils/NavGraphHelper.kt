package com.test.androiddevelopersexample.ui.utils

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.test.androiddevelopersexample.R

object NavGraphHelper {

    fun setGraph(activity: FragmentActivity?, graphId: Int, startDestination: Int = 0) {
        val myNavHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.fragmentNavHost) as NavHostFragment

        val inflater = myNavHostFragment.navController.navInflater

        val graph = inflater.inflate(graphId)

        if (startDestination != 0) {
            graph.startDestination = startDestination
        }

        val navController = activity.findNavController(R.id.fragmentNavHost)
        navController.graph = graph
    }
}