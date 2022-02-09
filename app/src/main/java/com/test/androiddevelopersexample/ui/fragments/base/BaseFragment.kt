package com.test.androiddevelopersexample.ui.fragments.base

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.activities.NavigationActivity


/**
 * Created by ignaciodeandreisdenis on 7/24/20.
 */
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    open var screenTag = "BaseFragment"
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    open var showBottomNavigation = false
    var fromDeepLink = false

    private var screenHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(screenTag, "onCreate")
        screenHeight = resources.displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(screenTag, "onCreateView")
        (activity as NavigationActivity).showBottomNavigationMenu(showBottomNavigation)
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(screenTag, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeListener = true
        Log.e(screenTag, "onDestroyView")
    }

    fun setNavigation(toolbar: Toolbar, fragmentId: Int) {
        toolbar.setNavigationIcon(R.drawable.svg_back_arrow)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    private var isKeyboardShowing = false
    private var removeListener = false

    fun setKeyBoardListener(callback: (Boolean) -> Unit) {
        binding.apply {
            with(root.viewTreeObserver) {
                addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        Log.e(screenTag, "onGlobalLayout()")

                        if (removeListener) {
                            removeOnGlobalLayoutListener(this)
                        }

                        val r = Rect()
                        root.getWindowVisibleDisplayFrame(r)
                        val keypadHeight = screenHeight - r.bottom

                        if (keypadHeight > screenHeight * 0.15) {
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true
                                callback(true)
                            }
                        } else {
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false
                                callback(false)
                            }
                        }
                    }
                })
            }
        }
    }
}
