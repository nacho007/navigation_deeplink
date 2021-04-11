package com.test.androiddevelopersexample.ui.fragments.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity

/**
 * Created by ignaciodeandreisdenis on 7/24/20.
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    open var screenTag = "BaseFragment"
    protected abstract val binding: T?
    open var showBottomNavigation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(screenTag, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(screenTag, "onCreateView")
        (activity as MainActivity).showBottomNavigationMenu(showBottomNavigation)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(screenTag, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(screenTag, "onDestroyView")
    }

}
