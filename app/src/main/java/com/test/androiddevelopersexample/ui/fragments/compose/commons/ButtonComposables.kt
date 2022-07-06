package com.test.androiddevelopersexample.ui.fragments.compose.commons

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 6/7/22.
 */
@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    action: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = action,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {
        AstroText(
            text = text.uppercase(Locale.getDefault()),
            color = Color.White
        )
    }
}