package com.test.androiddevelopersexample.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.test.androiddevelopersexample.databinding.CustomCountryItemViewBinding
import com.test.androiddevelopersexample.ui.fragments.swipe.Country
import com.test.androiddevelopersexample.ui.utils.show

/**
 * Created by ignaciodeandreisdenis on 2/6/22.
 */
class CustomCountry @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var binding: CustomCountryItemViewBinding =
        CustomCountryItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {

    }

    fun setCountry(country: Country) {
        binding.tvCountryName.text = country.name
        binding.tvCountryPrefixNumber.text = country.callingCode
        binding.tvCountryPrefixNumber.show(true)
    }

}
