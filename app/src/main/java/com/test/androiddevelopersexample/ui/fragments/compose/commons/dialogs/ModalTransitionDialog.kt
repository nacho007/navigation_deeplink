package com.test.androiddevelopersexample.ui.fragments.compose.commons.dialogs

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.dialogBackgroundColor
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */
private const val DIALOG_BUILD_TIME = 200L

/**
 * [Dialog] which uses a modal transition to animate in and out its content.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ModalTransitionDialog(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = false,
    content: @Composable (ModalTransitionDialogHelper) -> Unit
) {
    val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateContentBackTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateContentBackTrigger.value = true
        }
        launch {
            onCloseSharedFlow.asSharedFlow().collectLatest {
                startDismissWithExitAnimation(
                    animateContentBackTrigger,
                    onDismissRequest
                )
            }
        }
    }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(
                    animateContentBackTrigger,
                    onDismissRequest
                )
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            DialogAnimation(visible = animateContentBackTrigger.value) {
                content(ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
            }
        }
    }
}

private suspend fun startDismissWithExitAnimation(
    animateContentBackTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateContentBackTrigger.value = false
    delay(ANIMATION_TIME)
    onDismissRequest()
}

/**
 * Helper class that can be used inside the content scope from
 * composables that implement the [ModalTransitionDialog] to hide
 * the [Dialog] with a modal transition animation
 */
class ModalTransitionDialogHelper(
    private val coroutineScope: CoroutineScope,
    private val onCloseFlow: MutableSharedFlow<Unit>
) {
    fun triggerAnimatedClose() {
        coroutineScope.launch {
            onCloseFlow.emit(Unit)
        }
    }
}

internal const val ANIMATION_TIME = 200L


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun DialogAnimation(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(250),
            initialOffsetY = { fullHeight ->
                +fullHeight
            }
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically(
            animationSpec = tween(250),
            targetOffsetY = { fullHeight ->
                +fullHeight
            }
        ) + fadeOut(),
        content = content
    )
}

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//internal fun DialogAnimation(
//    visible: Boolean,
//    content: @Composable AnimatedVisibilityScope.() -> Unit
//) {
//    AnimatedVisibility(
//        visible = visible,
//        enter = fadeIn(
//            animationSpec = tween(ANIMATION_TIME.toInt()),
//            initialAlpha = 0f,
//        ) + scaleIn(
//            animationSpec = tween(ANIMATION_TIME.toInt()),
//            transformOrigin = TransformOrigin.Center
//        ),
//        exit = fadeOut(
//            animationSpec = tween(ANIMATION_TIME.toInt()),
//        ) + scaleOut(
//            animationSpec = tween(ANIMATION_TIME.toInt()),
//            transformOrigin = TransformOrigin.Center
//        ),
//        content = content
//    )
//}


@Composable
fun BottomSheetLikeDialog(
    description: String,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(dialogBackgroundColor(), RoundedCornerShape(4.dp))
    ) {

        BodyText(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.mobile_error),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        BodyText(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(onClick = onConfirm)
            {
                BodyText(
                    text = stringResource(id = R.string.mobile_ok),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}


@Composable
fun CustomErrorDialog(
    description: String,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .background(dialogBackgroundColor(), RoundedCornerShape(4.dp))
    ) {

        BodyText(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.mobile_error),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        BodyText(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = description,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(onClick = onConfirm)
            {
                BodyText(
                    text = stringResource(id = R.string.mobile_ok),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun CustomPositiveNegativeDialog(
    title: String,
    description: String?,
    positiveText: String,
    negativeText: String,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .background(dialogBackgroundColor(), RoundedCornerShape(4.dp))
    ) {

        BodyText(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            text = title,
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        description?.let {
            BodyText(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                text = it,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onNegative)
            {
                BodyText(
                    text = negativeText,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }

            TextButton(onClick = onPositive)
            {
                BodyText(
                    text = positiveText,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

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
private fun Preview() {
    AstroPayTheme {
        Scaffold(modifier = Modifier.background(color = MaterialTheme.colors.background),
            content = {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomErrorDialog(
                        description = "My description",
                        onConfirm = {}
                    )

                    Spacer(
                        modifier = Modifier
                            .height(25.dp)
                            .width(50.dp)
                    )

                    CustomPositiveNegativeDialog(
                        title = "Title",
                        description = "My description",
                        positiveText = "Yes",
                        negativeText = "No",
                        onPositive = { /*TODO*/ }) {
                    }
                }
            }
        )
    }
}