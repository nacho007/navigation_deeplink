package com.test.androiddevelopersexample.ui.fragments.swipe

import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.CountryItemBinding
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
class CountryItemView(private val country: Country) :
    BindableItem<CountryItemBinding>() {

    override fun getLayout() = R.layout.country_item

    override fun initializeViewBinding(view: View) = CountryItemBinding.bind(view)

    override fun bind(viewBinding: CountryItemBinding, position: Int) {
        viewBinding.customCountry.setCountry(country)
    }
}