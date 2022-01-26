package com.test.androiddevelopersexample.ui.fragments.swipe

import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.CountryItemViewBinding
import com.test.androiddevelopersexample.ui.utils.show
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
class CountryItemView(private val country: Country) :
    BindableItem<CountryItemViewBinding>() {

    override fun getLayout() = R.layout.country_item_view

    override fun initializeViewBinding(view: View) = CountryItemViewBinding.bind(view)

    override fun bind(viewBinding: CountryItemViewBinding, position: Int) {
        viewBinding.tvCountryName.text = country.name

        viewBinding.tvCountryPrefixNumber.text = country.callingCode
        viewBinding.tvCountryPrefixNumber.show(true)
    }
}