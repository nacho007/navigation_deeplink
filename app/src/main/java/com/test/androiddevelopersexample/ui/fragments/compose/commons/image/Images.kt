package com.test.androiddevelopersexample.ui.fragments.compose.commons.image

/**
 * Created by ignaciodeandreisdenis on 14/9/22.
 */
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.test.androiddevelopersexample.ui.utils.ImageExtension
import com.test.androiddevelopersexample.ui.utils.ImageHelper

@Composable
fun DefaultAsyncImage(
    image: String?,
    placeholderRes: Int,
    modifier: Modifier = Modifier,
    imageExtension: ImageExtension = ImageExtension.NONE,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha
) {
    if (image.isNullOrEmpty()) {
        Image(
            painter = painterResource(placeholderRes),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            alpha = alpha
        )
    } else {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ImageHelper.getFormattedPath(imageExtension, image))
                .build(),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
            alpha = alpha
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading,
                is AsyncImagePainter.State.Error -> Image(
                    painter = painterResource(placeholderRes),
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = modifier,
                    alpha = alpha
                )
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            }
        }
    }
}