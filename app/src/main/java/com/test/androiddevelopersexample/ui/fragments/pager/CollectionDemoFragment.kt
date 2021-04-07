package com.test.androiddevelopersexample.ui.fragments.pager

/**
 * Created by ignaciodeandreisdenis on 7/24/20.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.ui.fragments.base.FragmentBase
import kotlinx.android.synthetic.main.collection_demo_fragment.*
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Created by ignaciodeandreisdenis on 7/24/20.
 */
class CollectionDemoFragment : FragmentBase() {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.collection_demo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = view.findViewById(R.id.pager)
        demoCollectionAdapter = DemoCollectionAdapter(this)
        viewPager.adapter = demoCollectionAdapter

        TabLayoutMediator(tab_layout, viewPager) { tab, position ->
            tab.text = (position + 1).toString()
        }.attach()
    }
}
