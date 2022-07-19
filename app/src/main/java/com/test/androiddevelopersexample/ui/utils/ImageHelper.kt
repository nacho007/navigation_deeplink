package com.test.androiddevelopersexample.ui.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.Transformation
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.OPACITY_DISABLED

object ImageHelper {

    fun setImage(
        context: Context,
        imageView: ImageView,
        path: String?,
        imageExtension: ImageExtension = ImageExtension.SVG,
        placeHolderType: PlaceHolderType = PlaceHolderType.NONE,
        imageEffect: ImageEffect = ImageEffect.NONE,
        transformation: Transformation? = null,
        cache: Boolean? = true
    ) {
        setImageWithCoil(
            context = context,
            imageView = imageView,
            path = path,
            imageExtension = imageExtension,
            placeHolderType = placeHolderType,
            imageEffect = imageEffect,
            transformation = transformation,
            cache = cache
        )
    }

    private fun setImageWithCoil(
        context: Context,
        imageView: ImageView,
        path: String?,
        imageExtension: ImageExtension,
        placeHolderType: PlaceHolderType,
        imageEffect: ImageEffect,
        transformation: Transformation?,
        cache: Boolean?
    ) {
        val data = when {
            path.isNullOrEmpty() -> placeHolderType.resource
            else -> getFormattedPath(imageExtension, path)
        }

        when (imageEffect) {
            ImageEffect.NONE -> imageView.alpha = 1.0f
            ImageEffect.OPACITY -> imageView.alpha = OPACITY_DISABLED
            ImageEffect.CHANNEL_OPACITY -> {
                imageView.alpha = OPACITY_DISABLED
                imageView.setColorFilter(ContextCompat.getColor(context, R.color.color_icon))
            }
        }

        val request = if (transformation != null) {
            ImageRequest.Builder(context)
                .data(data)
                .crossfade(false)
                .target(imageView)
                .memoryCachePolicy(
                    if (cache == true) {
                        CachePolicy.ENABLED
                    } else {
                        CachePolicy.WRITE_ONLY
                    }
                )
                .diskCachePolicy(
                    if (cache == true) {
                        CachePolicy.ENABLED
                    } else {
                        CachePolicy.WRITE_ONLY
                    }
                )
                .transformations(transformation)
                .error(placeHolderType.resource)
                .placeholder(placeHolderType.resource)
                .build()
        } else {
            ImageRequest.Builder(context)
                .data(data)
                .crossfade(false)
                .target(imageView)
                .memoryCachePolicy(
                    if (cache == true) {
                        CachePolicy.ENABLED
                    } else {
                        CachePolicy.WRITE_ONLY
                    }
                )
                .diskCachePolicy(
                    if (cache == true) {
                        CachePolicy.ENABLED
                    } else {
                        CachePolicy.WRITE_ONLY
                    }
                )
                .error(placeHolderType.resource)
                .placeholder(placeHolderType.resource)
                .build()
        }

        context.imageLoader.enqueue(request)
    }

    fun getFormattedPath(type: ImageExtension = ImageExtension.SVG, path: String?): String? {
        return when (type) {
            ImageExtension.URL,
            ImageExtension.NONE,
            ImageExtension.DEFAULT -> path
            ImageExtension.PNG_3X,
            ImageExtension.SVG,
            ImageExtension.GIF,
            ImageExtension.PNG -> path.plus(type.typeName)
        }
    }
}

enum class ImageEffect {
    NONE, OPACITY, CHANNEL_OPACITY
}

enum class ImageExtension(val typeName: String) {
    PNG(".png"),
    PNG_3X("@3x.png"),
    SVG(".svg"),
    GIF(".gif"),
    DEFAULT("default"),
    NONE("none"),
    URL("url")
}

enum class PlaceHolderType(val resource: Int) {
    BANK(R.drawable.svg_bank_deposit),
    CAMERA(R.drawable.svg_camera),
    CAMERA_INVERTED(R.drawable.svg_camera_inverted),
    CUBE(R.drawable.svg_loading_cube),
    MERCHANT(R.drawable.svg_merchant),
    USER(R.mipmap.user_avatar),
    NONE(R.drawable.svg_none),
    BLANK(R.drawable.svg_image_place_holder),
    CASH(R.drawable.svg_withdrawal)
}