package com.test.androiddevelopersexample.ui.fragments.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import java.util.*

/**
 * Created by ignaciodeandreisdenis on 24/5/22.
 */

@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    action: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .height(45.dp),
        onClick = action,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {
        Text(
            text = text.uppercase(Locale.getDefault())
        )
    }
}

@Composable
fun AstroOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.onSurface,
        focusedLabelColor = MaterialTheme.colors.onSurface,
        cursorColor = MaterialTheme.colors.onSurface
    ),
    onScrollToPosition: ((yPosition: Int) -> Unit)? = null
) {
    var currentYPosition = 0

    OutlinedTextField(
        enabled = enabled,
        readOnly = readOnly,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                currentYPosition = coordinates.positionInRoot().y.toInt()
            }
            .onFocusEvent { focusState ->
                if (focusState.hasFocus) {
                    onScrollToPosition?.invoke(currentYPosition)
                }
            },
        singleLine = singleLine,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}