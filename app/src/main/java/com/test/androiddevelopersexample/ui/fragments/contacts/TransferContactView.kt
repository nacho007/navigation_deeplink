package com.test.androiddevelopersexample.ui.fragments.contacts

import android.content.Context
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.TransferContactItemBinding
import com.test.androiddevelopersexample.ui.utils.ImageExtension
import com.test.androiddevelopersexample.ui.utils.ImageHelper
import com.test.androiddevelopersexample.ui.utils.PlaceHolderType
import com.test.androiddevelopersexample.ui.utils.show
import com.xwray.groupie.viewbinding.BindableItem
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 22/2/22.
 */
class TransferContactView(
    private val contact: ContactsFragment.Contact,
    private val index: Int,
    private val listSize: Int,
    private val searchTerm: String
) : BindableItem<TransferContactItemBinding>() {

    override fun getLayout() = R.layout.transfer_contact_item

    override fun initializeViewBinding(view: View) = TransferContactItemBinding.bind(view)

    override fun bind(binding: TransferContactItemBinding, position: Int) {
        binding.apply {
            tvName.show(false)
            tvNameBold.show(false)
        }

        when (contact) {
            is ContactsFragment.Contact.Recent -> setData(binding = binding, contact = contact, isRecent = true)
            is ContactsFragment.Contact.Phone -> setData(binding = binding, contact = contact, isRecent = false)
        }

        when (index) {
            0 -> {
                val onlyOne = listSize - 1 == index
                val background = if (onlyOne) {
                    R.drawable.shape_balloon_alone_selector
                } else {
                    R.drawable.shape_balloon_top_selector
                }
                setBackground(binding, onlyOne.not(), background)
            }
            listSize - 1 -> setBackground(binding, false, R.drawable.shape_balloon_bottom_selector)
            else -> setBackground(binding, true, R.drawable.shape_balloon_middle_rect_selector)
        }
    }

    private fun setBackground(
        binding: TransferContactItemBinding,
        showDivider: Boolean,
        background: Int
    ) {
        binding.apply {
            vDivider.show(showDivider)
            clContainer.setBackgroundResource(background)
        }
    }

    private fun setData(binding: TransferContactItemBinding, contact: ContactsFragment.Contact, isRecent: Boolean) {
        binding.apply {
            ivImage.transitionName = "ivImage ${contact.number}}"
            ivAstropay.transitionName = "ivLogo ${contact.number}}"
            ivAstropay.show(isRecent)
            tvRecent.show(isRecent)
            setImage(root.context, contact.image, ivImage)
            showName(if (isRecent) tvNameBold else tvName, root.context)
            tvNumber.text = formatSearchTerm(binding.root.context, contact.number, searchTerm)
        }
    }

    private fun showName(tv: TextView, context: Context) {
        tv.apply {
            contact.name?.let {
                show(true)
                text = formatSearchTerm(context, it, searchTerm)
            } ?: show(false)
        }
    }

    private fun formatSearchTerm(
        context: Context,
        toReplace: String,
        searchTerm: String
    ): Spannable {
        val out = toReplace.toSpannable()

        val startPosition = toReplace.lowercase(Locale.getDefault())
            .indexOf(searchTerm.lowercase(Locale.getDefault()))
        val endPosition = searchTerm.length + startPosition

        if (startPosition != -1) {
            out.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_accent)),
                startPosition,
                endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return out
    }

    private fun setImage(context: Context, image: String?, ivImage: CircleImageView) {
        ImageHelper.setImage(
            imageProvider = ImageHelper.ImageProvider.Coil(context),
            imageView = ivImage,
            path = image,
            placeHolderType = PlaceHolderType.USER,
            imageExtension = ImageExtension.NONE
        )
    }
}
