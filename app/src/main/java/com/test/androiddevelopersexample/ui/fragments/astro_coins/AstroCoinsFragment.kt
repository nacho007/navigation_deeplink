package com.test.androiddevelopersexample.ui.fragments.astro_coins

import android.os.Bundle
import com.test.androiddevelopersexample.databinding.FragmentAstroCoinsBinding
import com.test.androiddevelopersexample.ui.activities.MainActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class AstroCoinsFragment : BaseFragment<FragmentAstroCoinsBinding>() {

    override var screenTag = "AstroCoinsFragment"
    override val binding by lazy { FragmentAstroCoinsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).showBottomNavigationMenu(false)
    }
}
