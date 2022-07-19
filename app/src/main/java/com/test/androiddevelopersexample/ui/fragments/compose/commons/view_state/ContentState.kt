package com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.compose.commons.loaders.LottieLoader
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IntentionOrNull
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

internal const val ANIMATION_TIME = 250L

enum class Type {
    EMPTY, LOAD_BLACK_OPACITY, LOAD_LIGHT, HIDE, NETWORK_ERROR, NONE
}

@Composable
fun ContentState(
    state: Type,
    lastIntention: IntentionOrNull,
    content: @Composable () -> Unit
) {
    val animate = when (state) {
        Type.EMPTY -> false
        Type.LOAD_BLACK_OPACITY -> true
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
        ),
        exit = fadeOut(
            animationSpec = tween(ANIMATION_TIME.toInt()),
        )
    ) {
        content()
    }

    when (state) {
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

                BodyText(
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

                BodyText(
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

        Type.HIDE,
        Type.NONE -> {

        }
    }
}

const val ASTRONAUT_ANIMATION = "astronaut_light_theme.json"
const val NO_INTERNET = "no_internet.json"
const val LOADING = "loading.json"


@Composable
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = ""
)
@Preview(
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = ""
)
private fun ContentStatePreview() {
    AstroPayTheme {
        ContentState(
            state = Type.NETWORK_ERROR,
            lastIntention = { }
        ) {
            BodyText(text = "Content!")
        }
    }
}
