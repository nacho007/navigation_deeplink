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