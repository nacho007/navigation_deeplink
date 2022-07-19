package com.test.androiddevelopersexample.ui.custom

/**
 * Created by ignaciodeandreisdenis on 23/2/22.
 */
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.LayoutPhoneNumberBinding
import com.test.androiddevelopersexample.ui.utils.ImageHelper
import com.test.androiddevelopersexample.ui.utils.PlaceHolderType

/**
 * Created by ignaciodeandreisdenis on 9/22/20.
 */
class PhoneNumberLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = LayoutPhoneNumberBinding.inflate(LayoutInflater.from(context), this)
    private var listener: PhoneNumberListener? = null

    init {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.PhoneNumberLayout)

            val iconTintColor = attributes.getInt(
                R.styleable.PhoneNumberLayout_phone_country_flag_tint,
                R.color.color_black
            )
//            setIconTint(iconTintColor)

            val phonePrefixText =
                attributes.getString(R.styleable.PhoneNumberLayout_phone_number_prefix)
            val phonePrefixColor = attributes.getInt(
                R.styleable.PhoneNumberLayout_phone_number_prefix_color,
                R.color.color_black
            )
            setPhonePrefixText(phonePrefixText)
            setPhonePrefixColor(phonePrefixColor)

            val phoneNumberText = attributes.getString(R.styleable.PhoneNumberLayout_phone_number)
            val phoneNumberColor = attributes.getInt(
                R.styleable.PhoneNumberLayout_phone_number_color,
                R.color.color_black
            )
            setPhoneNumberText(phoneNumberText)

            val phoneNumberHintText =
                attributes.getString(R.styleable.PhoneNumberLayout_phone_number_hint)
            val phoneNumberHintColor = attributes.getInt(
                R.styleable.PhoneNumberLayout_phone_number_hint_color,
                R.color.color_black
            )
            setPhoneNumberColor(phoneNumberColor)
            setPhoneNumberHintColor(phoneNumberHintColor)
            setPhoneNumberHintText(phoneNumberHintText)

            attributes.recycle()
        }
    }

    fun setIcon(url: String?) {
        ImageHelper.setImage(
            context = context,
            imageView = binding.ivPhoneCountryFlag,
            path = url,
            placeHolderType = PlaceHolderType.CAMERA
        )
    }

    private fun setIconTint(color: Int) {
        ImageViewCompat.setImageTintList(binding.ivPhoneCountryFlag, ColorStateList.valueOf(color))
    }

    fun setPhonePrefixText(text: String?) {
        binding.tvCountryPhonePrefix.text = text.toString()
    }

    private fun setPhonePrefixColor(color: Int) {
        binding.tvCountryPhonePrefix.setTextColor(color)
    }

    fun setPhoneNumberText(text: String?) {
        binding.etPhoneNumber.setText(text)
    }

    fun setPhoneNumberHintText(text: String?) {
        binding.etPhoneNumber.hint = text
    }

    private fun setPhoneNumberColor(color: Int) {
        binding.etPhoneNumber.setTextColor(color)
    }

    private fun setPhoneNumberHintColor(color: Int) {
        binding.etPhoneNumber.setHintTextColor(color)
    }

    fun getPhoneEditText(): EditText {
        return binding.etPhoneNumber
    }

    fun getPhoneNumber(): String {
        return context.getString(
            R.string.two_values,
            binding.tvCountryPhonePrefix.text,
            binding.etPhoneNumber.text.toString().replace(" ", "")
        )
    }

    fun setCountryCodeLister(func: () -> Unit) {

    }

    fun setListener(listener: PhoneNumberListener) {
        this.listener = listener
    }

    fun setEnabled() {
        binding.etPhoneNumber.isEnabled = true
        binding.etPhoneNumber.isFocusableInTouchMode = true
        binding.llPhoneChangeCountry.isEnabled = true
        binding.llPhoneChangeCountry.isFocusable = true
    }

    fun setDisabled() {
        binding.etPhoneNumber.isEnabled = false
        binding.etPhoneNumber.isFocusableInTouchMode = false
        binding.llPhoneChangeCountry.isEnabled = false
        binding.llPhoneChangeCountry.isFocusable = false
    }

    interface PhoneNumberListener {
        fun onTextChange(isFilled: Boolean)
    }
}
