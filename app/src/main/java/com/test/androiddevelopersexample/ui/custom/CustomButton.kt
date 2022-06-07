package com.test.androiddevelopersexample.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
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

        attrs?.let {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

            val drawableTintStart = attributes.getResourceId(
                R.styleable.CustomButton_drawable_tint_start,
                R.color.color_red
            )

            val drawableTintEnd = attributes.getResourceId(
                R.styleable.CustomButton_drawable_tint_end,
                R.color.color_red
            )

            binding.apply {
                val drawableStart = ContextCompat.getDrawable(context, R.drawable.svg_fidelity)
                val drawableEnd = ContextCompat.getDrawable(context, R.drawable.svg_arrow_right)

                drawableStart?.apply {
                    setTint(ContextCompat.getColor(context, drawableTintStart))
                }

                drawableEnd?.apply {
                    setTint(ContextCompat.getColor(context, drawableTintEnd))
                }
            }

            attributes.recycle()
        }
    }

}