package com.test.androiddevelopersexample.ui.fragments.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.ARG_OBJECT
import kotlinx.android.synthetic.main.fragment_collection_object.*

/**
 * Created by ignaciodeandreisdenis on 7/24/20.
 */
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
            text1.text = getInt(ARG_OBJECT).toString()
        }
    }
}