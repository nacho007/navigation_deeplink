package com.test.androiddevelopersexample.ui.fragments.compose.commons.images

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.test.androiddevelopersexample.ui.utils.ImageExtension
import com.test.androiddevelopersexample.ui.utils.ImageHelper

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
@Composable
fun AstroAsyncImage(
    image: String?,
    placeholderRes: Int,
    modifier: Modifier = Modifier,
    imageExtension: ImageExtension = ImageExtension.NONE,
    contentScale: ContentScale = ContentScale.Fit,
) {
    if (image.isNullOrEmpty()) {
        Image(
            painter = painterResource(placeholderRes),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
        )
    } else {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImageHelper.getFormattedPath(imageExtension, image))
                .build(),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading,
                is AsyncImagePainter.State.Error -> Image(
                    painter = painterResource(placeholderRes),
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = modifier,
                )
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()

            }
        }
    }
}