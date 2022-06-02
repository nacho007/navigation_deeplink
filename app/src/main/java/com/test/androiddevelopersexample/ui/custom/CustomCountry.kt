package com.test.androiddevelopersexample.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
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
) : MaterialCardView(context, attrs, defStyle) {

    private var binding: CustomCountryItemViewBinding =
        CustomCountryItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.root.setOnClickListener {
            super.callOnClick()
            Toast.makeText(context, "Custom Pressed", Toast.LENGTH_SHORT).show()
        }
    }

    fun setCountry(country: Country) {
        binding.tvCountryName.text = country.name
        binding.tvCountryPrefixNumber.text = country.callingCode
        binding.tvCountryPrefixNumber.show(true)
    }

}
