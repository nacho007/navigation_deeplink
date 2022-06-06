package com.test.androiddevelopersexample.ui.fragments.help_center

import android.os.Bundle
import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentArticleBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 4/10/21.
 */
class ArticleFragment : BaseFragment<FragmentArticleBinding>(FragmentArticleBinding::inflate) {

    override var screenTag = "ArticleFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.articleFragment)
        }
    }

}
