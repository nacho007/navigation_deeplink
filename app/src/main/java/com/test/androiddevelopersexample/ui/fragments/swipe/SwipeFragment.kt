package com.test.androiddevelopersexample.ui.fragments.swipe

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentSwipeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.SimpleDividerItemDecoration
import com.test.androiddevelopersexample.ui.utils.Utils
import com.test.androiddevelopersexample.ui.utils.navigate
import com.test.androiddevelopersexample.ui.utils.setOnSingleItemClickListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
class SwipeFragment : BaseFragment<FragmentSwipeBinding>(FragmentSwipeBinding::inflate) {

    override var screenTag = "SwipeFragment"

    override var showBottomNavigation: Boolean = false

    private var countryAdapter = GroupAdapter<GroupieViewHolder>()
    private var countriesViewList = emptyList<CountryItemView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            swipeListBtn.setOnClickListener {
                navigate(R.id.action_swipeFragment_to_swipeFragmentList)
            }

            swipeCustomBtn.setOnClickListener {
                navigate(R.id.action_swipeFragment_to_swipeFragmentCustom)
            }
        }
    }
}
