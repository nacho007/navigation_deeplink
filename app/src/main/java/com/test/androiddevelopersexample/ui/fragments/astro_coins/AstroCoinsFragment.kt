package com.test.androiddevelopersexample.ui.fragments.astro_coins

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentAstroCoinsBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class AstroCoinsFragment : BaseFragment<FragmentAstroCoinsBinding>() {

    override var screenTag = "AstroCoinsFragment"
    override val binding by lazy { FragmentAstroCoinsBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.astroCoinsFragment)
        }
    }
}
