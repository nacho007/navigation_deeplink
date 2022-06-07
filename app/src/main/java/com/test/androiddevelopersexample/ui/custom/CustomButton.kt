package com.test.androiddevelopersexample.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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

            val drawableTintStart = ContextCompat.getColor(
                context, attributes.getResourceId(
                    R.styleable.CustomButton_drawable_tint_start,
                    R.color.color_red
                )
            )

            val drawableTintEnd = ContextCompat.getColor(
                context, attributes.getResourceId(
                    R.styleable.CustomButton_drawable_tint_end,
                    R.color.color_red
                )
            )

            binding.apply {
                val compoundDrawables: Array<Drawable> = tvText.compoundDrawablesRelative

                val startCompoundDrawable = compoundDrawables[0]
                val endCompoundDrawable = compoundDrawables[2]

                DrawableCompat.setTint(startCompoundDrawable, drawableTintStart)
                DrawableCompat.setTint(endCompoundDrawable, drawableTintEnd)
            }

            attributes.recycle()
        }
    }

}