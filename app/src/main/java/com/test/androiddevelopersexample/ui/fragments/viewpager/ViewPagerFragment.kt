package com.test.androiddevelopersexample.ui.fragments.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentViewpagerBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 11/12/21.
 */
private const val ARG_OBJECT = "object"

class ViewPagerFragment  : BaseFragment<FragmentViewpagerBinding>(FragmentViewpagerBinding::inflate) {

    override var screenTag = "ViewPagerFragment"

    lateinit var demoCollectionAdapter: DemoCollectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setNavigation(ablToolbar.toolbar, R.id.astroCoinsFragment)

            demoCollectionAdapter = DemoCollectionAdapter(this@ViewPagerFragment)
            viewPager.adapter = demoCollectionAdapter

            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        }
    }

    class DemoCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            val fragment = DemoObjectFragment()
            fragment.arguments = Bundle().apply {
                // Our object is just an integer :-P
                putInt(ARG_OBJECT, position + 1)
            }
            return fragment
        }
    }

    // Instances of this class are fragments representing a single
// object in our collection.
    class DemoObjectFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return inflater.inflate(R.layout.fragment_collection_object, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
                val textView: TextView = view.findViewById(R.id.tv_content)
                val text = getInt(ARG_OBJECT).toString()
                textView.text = text
            }
        }
    }

    override val fragmentName: String
        get() = "ViewPagerFragment"
    override val screenName: String
        get() = "ViewPagerFragment"
}
