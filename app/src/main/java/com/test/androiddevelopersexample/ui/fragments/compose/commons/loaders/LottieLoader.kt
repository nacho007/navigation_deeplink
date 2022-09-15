package com.test.androiddevelopersexample.ui.fragments.compose.commons.loaders

/**
 * Created by ignaciodeandreisdenis on 7/6/22.
 */
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    modifier: Modifier,
    lottieResource: String
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieResource))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
fun LottieLoaderWithProgress(
    modifier: Modifier,
    lottieResource: String,
    onCompleted: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieResource))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        restartOnPlay = false,
        clipSpec = null,
        speed = 1f,
        iterations = 1,
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { progress },
    )

    if (progress == 1F) {
        onCompleted()
    }
}