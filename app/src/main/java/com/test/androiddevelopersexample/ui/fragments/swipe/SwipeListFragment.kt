package com.test.androiddevelopersexample.ui.fragments.swipe

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentSwipeListBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.utils.SimpleDividerItemDecoration
import com.test.androiddevelopersexample.ui.utils.Utils
import com.test.androiddevelopersexample.ui.utils.setOnSingleItemClickListener
import com.test.androiddevelopersexample.ui.utils.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs


/**
 * Created by ignaciodeandreisdenis on 27/1/22.
 */
class SwipeListFragment :
    BaseFragment<FragmentSwipeListBinding>(FragmentSwipeListBinding::inflate) {

    override var screenTag = "SwipeListFragment"

    override var showBottomNavigation: Boolean = false

    private var countryAdapter = GroupAdapter<GroupieViewHolder>()
    private var countriesViewList = emptyList<CountryItemView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.swipeFragmentList)

            etSearch.setOnClickListener {
                ablToolbar.abl.setExpanded(false)
            }

            etSearch.setOnFocusChangeListener { view, b ->
                if (b) {
                    ablToolbar.abl.setExpanded(false)
                }
            }

            setKeyBoardListener { keyboardOpen ->
                if (keyboardOpen) {
                    fabManualTransfer.show(false)
                    Log.e(screenTag, "Keyboard opened!")
                } else {
                    fabManualTransfer.show(true)
                    Log.e(screenTag, "Keyboard closed")
                }
            }

            rvCountries.apply {
                adapter = countryAdapter.apply {
                    setOnSingleItemClickListener { item, _ ->

                    }
                }
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    SimpleDividerItemDecoration(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.shape_divider_list_item
                        )
                    )
                )
            }
        }

        val json = Utils.loadJSONFromAsset(requireContext(), "countries.json")

        val gSon = GsonBuilder().serializeNulls().create()
        val type = object : TypeToken<MockCountries>() {

        }.type
        val mockCountries = gSon.fromJson<MockCountries>(json, type)
        countriesViewList = mockCountries.countries.map { country -> CountryItemView(country) }
        countryAdapter.update(countriesViewList)

    }
}
