package com.test.androiddevelopersexample.ui.custom


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroText
import com.test.androiddevelopersexample.ui.fragments.compose.dialogs.ANIMATION_TIME

/**
 * Created by ignaciodeandreisdenis on 7/6/22.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentState(
    state: Type,
    lastIntention: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val animate = when (state) {
        Type.EMPTY -> false
        Type.LOAD_BLACK_OPACITY -> false
        Type.LOAD_LIGHT -> false
        Type.HIDE -> true
        Type.NETWORK_ERROR -> false
        Type.NONE -> false
    }

    AnimatedVisibility(
        visible = animate,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = ANIMATION_TIME.toInt(),
            ),
            initialAlpha = 0f,
        ) + scaleIn(
            animationSpec = tween(
                durationMillis = ANIMATION_TIME.toInt(),
            ),
            transformOrigin = TransformOrigin.Center
        ),
        exit = fadeOut(
            animationSpec = tween(ANIMATION_TIME.toInt()),
        ) + scaleOut(
            animationSpec = tween(ANIMATION_TIME.toInt()),
            transformOrigin = TransformOrigin.Center
        )
    ) {
        content()
    }

    when (state) {
        Type.NONE -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.color_blue))
            ) {
                AstroText(modifier = Modifier.align(Alignment.Center), text = "STATE NONE")
            }
        }
        Type.EMPTY -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieLoader(
                    modifier = Modifier.size(150.dp),
                    lottieResource = ASTRONAUT_ANIMATION
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.mobile_no_results_found),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                lastIntention?.let { lastIntention ->
                    DefaultButton(
                        modifier = Modifier,
                        text = stringResource(R.string.mobile_refresh),
                        action = { lastIntention() }
                    )
                }
            }
        }

        Type.LOAD_BLACK_OPACITY -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.color_black_opacity_75)),
                contentAlignment = Alignment.Center
            ) {
                LottieLoader(
                    modifier = Modifier.size(120.dp),
                    lottieResource = LOADING
                )
            }
        }

        Type.LOAD_LIGHT -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LottieLoader(
                    modifier = Modifier.size(120.dp),
                    lottieResource = LOADING
                )
            }
        }

        Type.NETWORK_ERROR -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieLoader(
                    modifier = Modifier.size(150.dp),
                    lottieResource = NO_INTERNET
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.mobile_internet_error),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                lastIntention?.let { lastIntention ->
                    DefaultButton(
                        modifier = Modifier,
                        text = stringResource(R.string.mobile_refresh),
                        action = { lastIntention() }
                    )
                }
            }
        }

        Type.HIDE -> {

        }
    }
}

const val ASTRONAUT_ANIMATION = "astronaut_light_theme.json"
const val NO_INTERNET = "no_internet.json"
const val LOADING = "loading.json"

enum class Type {
    EMPTY, LOAD_BLACK_OPACITY, LOAD_LIGHT, HIDE, NETWORK_ERROR, NONE
}

@Composable
@Preview
private fun ContentStatePreview() {
    AstroPayTheme {
        ContentState(
            state = Type.NONE,
            lastIntention = { }
        ) {
            Text(text = "Content!")
        }
    }
}