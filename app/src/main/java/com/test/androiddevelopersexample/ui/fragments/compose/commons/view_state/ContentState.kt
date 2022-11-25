package com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey300
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IntentionOrNull
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.loaders.LottieLoader
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.DefaultToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.previews.DefaultPreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal const val ANIMATION_TIME = 250L

enum class Type {
    EMPTY, EMPTY_WITH_REFRESH, LOAD_BLACK_OPACITY, LOAD_LIGHT, SHOW_CONTENT, NETWORK_ERROR, ANIMATION, DEFAULT_ERROR, SNACKBAR, NONE
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContentState(
    state: Type,
    lastIntention: IntentionOrNull,
    toolbar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onSnackBarDismissed: (() -> Unit)?,
    customEmptyState: (@Composable () -> Unit)? = null,
    customAnimation: (@Composable () -> Unit)? = null,
    floatingButton: @Composable () -> Unit,
    floatingActionButtonPosition: FabPosition = FabPosition.End
) {
    val showToolbar = when (state) {
        Type.EMPTY -> true
        Type.EMPTY_WITH_REFRESH -> true
        Type.LOAD_BLACK_OPACITY -> true
        Type.LOAD_LIGHT -> true
        Type.SHOW_CONTENT -> true
        Type.NETWORK_ERROR -> false
        Type.ANIMATION -> true
        Type.DEFAULT_ERROR -> true
        Type.SNACKBAR -> true
        Type.NONE -> false
    }

    val showContent = when (state) {
        Type.EMPTY -> false
        Type.EMPTY_WITH_REFRESH -> false
        Type.LOAD_BLACK_OPACITY -> true
        Type.LOAD_LIGHT -> false
        Type.SHOW_CONTENT -> true
        Type.NETWORK_ERROR -> false
        Type.ANIMATION -> true
        Type.DEFAULT_ERROR -> false
        Type.SNACKBAR -> true
        Type.NONE -> false
    }

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                Show(
                    showContent = showToolbar,
                    animate = true
                ) {
                    toolbar()
                }
            },
            content = {
                Show(
                    showContent = showContent,
                    animate = true
                ) {
                    content()
                }
                ShowDefaultError(state == Type.DEFAULT_ERROR, lastIntention)
                ShowEmpty(state == Type.EMPTY, customEmptyState)
                ShowEmptyWithRefresh(state == Type.EMPTY_WITH_REFRESH, lastIntention)
                ShowLoadLight(state == Type.LOAD_LIGHT)
                ShowConnectionError(state == Type.NETWORK_ERROR, lastIntention)
                SnackbarDemo(
                    state == Type.SNACKBAR,
                    onSnackBarDismissed,
                    scaffoldState,
                    coroutineScope
                )
            },
            floatingActionButton = {
                floatingButton()
            },
            floatingActionButtonPosition = floatingActionButtonPosition
        )

        if (customAnimation != null && state == Type.ANIMATION) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                customAnimation()
            }
        }
    }

    ShowLoadBlack(state == Type.LOAD_BLACK_OPACITY)
}

@Composable
fun SnackbarDemo(
    showContent: Boolean,
    onSnackBarDismissed: (() -> Unit)?,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    if (showContent) {
        LaunchedEffect(coroutineScope) {
            this.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "This is your message",
                    actionLabel = "Do something"
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> onSnackBarDismissed?.invoke()
                    SnackbarResult.ActionPerformed -> onSnackBarDismissed?.invoke()
                }
            }
        }
    }
}

@Composable
private fun ShowDefaultError(showContent: Boolean, lastIntention: IntentionOrNull) {
    Show(showContent = showContent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(65.dp),
                painter = painterResource(id = R.drawable.svg_build),
                contentDescription = null,
                tint = Grey300
            )

            Spacer(modifier = Modifier.height(16.dp))

            BodyText(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.home_error_screen_title),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            BodyText(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.home_error_screen_subtitle),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            lastIntention?.let { lastIntention ->
                Spacer(modifier = Modifier.height(8.dp))
                DefaultButton(
                    modifier = Modifier,
                    text = stringResource(R.string.mobile_retry),
                    action = { lastIntention() }
                )
            }

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
private fun ShowEmpty(showContent: Boolean, customEmptyState: @Composable (() -> Unit)?) {
    Show(showContent = showContent) {
        customEmptyState?.let { view ->
            view()
        } ?: run {
            DefaultEmpty()
        }
    }
}

@Composable
private fun DefaultEmpty() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoader(
            modifier = Modifier.size(150.dp),
            lottieResource = ASTRONAUT_ANIMATION
        )

        BodyText(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.mobile_no_results_found),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ShowEmptyWithRefresh(showContent: Boolean, lastIntention: IntentionOrNull) {
    Show(showContent = showContent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
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
}

@Composable
private fun ShowLoadBlack(showContent: Boolean) {
    Show(showContent = showContent) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.color_black_opacity_50))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            contentAlignment = Alignment.Center
        ) {
            LottieLoader(
                modifier = Modifier.size(120.dp),
                lottieResource = LOADING
            )
        }
    }
}

@Composable
private fun ShowLoadLight(showContent: Boolean) {
    Show(showContent = showContent) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
            contentAlignment = Alignment.Center
        ) {
            LottieLoader(
                modifier = Modifier.size(120.dp),
                lottieResource = LOADING
            )
        }
    }
}

@Composable
private fun ShowConnectionError(showContent: Boolean, lastIntention: IntentionOrNull) {
    Show(showContent = showContent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {},
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
}

@Composable
private fun Show(showContent: Boolean, animate: Boolean = false, content: @Composable () -> Unit) {
    if (animate) {
        AnimatedVisibility(
            visible = showContent,
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
    } else if (showContent) {
        content()
    }
}

const val ASTRONAUT_ANIMATION = "astronaut_light_theme.json"
const val NO_INTERNET = "no_internet.json"
const val LOADING = "loading.json"


@Composable
@DefaultPreview
private fun ContentStatePreview() {
    AstroPayTheme {
        ContentState(
            state = Type.NETWORK_ERROR,
            lastIntention = { },
            onSnackBarDismissed = {},
            toolbar = {
                DefaultToolBar(
                    title = "toolbar",
                ) {
                    IconNavigationBack {

                    }
                }
            },
            content = { BodyText(text = "Content!") },
            floatingButton = {}
        )
    }
}
