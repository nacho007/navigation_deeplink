package com.test.androiddevelopersexample.ui.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import coil.imageLoader
import coil.request.ImageRequest
import com.test.androiddevelopersexample.R

object ImageHelper {

    fun setImage(
        imageProvider: ImageProvider,
        imageView: ImageView,
        path: String?,
        imageExtension: ImageExtension = ImageExtension.SVG,
        placeHolderType: PlaceHolderType = PlaceHolderType.NONE
    ) {
        when (imageProvider) {
            is ImageProvider.Coil -> {
                val imageLoader = imageProvider.context.imageLoader

                val data = when {
                    path.isNullOrEmpty() -> placeHolderType.resource
                    else -> getFormattedPath(imageExtension, path)
                }

                val request = ImageRequest.Builder(imageProvider.context)
                    .data(data)
                    .crossfade(true)
                    .target(imageView)
                    .placeholder(placeHolderType.resource)
                    .build()
                imageLoader.enqueue(request)
            }
        }
    }

    fun setImageFromUri(
        imageProvider: ImageProvider,
        imageView: ImageView,
        uri: Uri,
        placeHolderType: PlaceHolderType = PlaceHolderType.NONE,
    ) {
        when (imageProvider) {
            is ImageProvider.Coil -> {
                val imageLoader = imageProvider.context.imageLoader
                val request = ImageRequest.Builder(imageProvider.context)
                    .data(uri)
                    .crossfade(true)
                    .target(imageView)
                    .placeholder(placeHolderType.resource)
                    .build()
                imageLoader.enqueue(request)
            }
        }
    }

    private fun getFormattedPath(type: ImageExtension, path: String?): String? {
        return when (type) {
            ImageExtension.NONE,
            ImageExtension.DEFAULT -> path
            ImageExtension.PNG_3X,
            ImageExtension.SVG,
            ImageExtension.GIF,
            ImageExtension.PNG -> path.plus(type.typeName)
        }
    }

    sealed class ImageProvider {
        data class Glide(
            val context: Context
        ) : ImageProvider()

        data class Coil(
            val context: Context,
        ) : ImageProvider()
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
    NONE("none")
}

enum class PlaceHolderType(val resource: Int) {
    BANK(R.drawable.svg_bank_deposit),
    CAMERA(R.drawable.svg_camera),
    CUBE(R.drawable.svg_loading_cube),
    USER(R.mipmap.user_avatar),
    NONE(R.drawable.svg_none),
}