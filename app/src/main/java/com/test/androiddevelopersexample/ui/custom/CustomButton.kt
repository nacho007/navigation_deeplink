package com.test.androiddevelopersexample.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.CustomButtonBinding

/**
 * Created by ignaciodeandreisdenis on 7/6/22.
 */
class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyle) {

    private var binding: CustomButtonBinding =
        CustomButtonBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.root.setOnClickListener {
            Toast.makeText(context, "Custom Pressed", Toast.LENGTH_SHORT).show()
        }
    }

}