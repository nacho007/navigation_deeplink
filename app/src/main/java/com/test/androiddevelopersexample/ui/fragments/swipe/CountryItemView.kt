package com.test.androiddevelopersexample.ui.fragments.swipe

import android.view.View
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.CountryItemBinding
import com.test.androiddevelopersexample.databinding.CustomCountryItemViewBinding
import com.test.androiddevelopersexample.ui.utils.show
import com.xwray.groupie.viewbinding.BindableItem

/**
 * Created by ignaciodeandreisdenis on 26/1/22.
 */
class CountryItemView(private val country: Country) :
    BindableItem<CustomCountryItemViewBinding>() {

    override fun getLayout() = R.layout.custom_country_item_view

    override fun initializeViewBinding(view: View) = CustomCountryItemViewBinding.bind(view)

    override fun bind(viewBinding: CustomCountryItemViewBinding, position: Int) {
//        viewBinding.customCountry.setCountry(country)
        viewBinding.root.alpha = 0.1f
        viewBinding.tvCountryName.text = country.name
        viewBinding.tvCountryPrefixNumber.text = country.callingCode
        viewBinding.tvCountryPrefixNumber.show(true)
    }
}