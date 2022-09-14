package com.test.androiddevelopersexample.ui.fragments.compose.commons.text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.theme.SemanticError
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

/**
 * Created by ignaciodeandreisdenis on 19/7/22.
 */

@Composable
fun DefaultOutlinedTextField(
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

@Composable
fun DefaultExpandableDropdown(
    modifier: Modifier,
    label: String?,
    suggestions: List<String>,
    selectedText: String,
    onValueChange: (value: String, index: Int) -> Unit,
    expanded: Boolean,
    onExpand: (expand: Boolean) -> Unit,
) {
    var dropDownWidth by rememberSaveable { mutableStateOf(0) }
    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp
    } else {
        Icons.Filled.ArrowDropDown
    }
    Column(
        modifier = modifier
    ) {
        DefaultOutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    dropDownWidth = it.width
                }
                .clickable {
                    if (suggestions.size > 1) {
                        onExpand(!expanded)
                    }
                },
            label = { label?.let { BodyText(label) } },
            trailingIcon = {
                if (suggestions.size > 1) {
                    Icon(icon, "contentDescription")
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                focusedLabelColor = MaterialTheme.colors.onSurface,
                disabledBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
                disabledLabelColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                disabledPlaceholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                disabledTextColor = MaterialTheme.colors.onSurface
            ),
            enabled = false,
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpand(false)
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { dropDownWidth.toDp() }),
        ) {
            suggestions.forEachIndexed { index, label ->
                DropdownMenuItem(onClick = {
                    onValueChange(label, index)
                    onExpand(false)
                }) {
                    BodyText(text = label)
                }
            }
        }
    }
}

@Composable
fun DefaultDropdown(
    modifier: Modifier,
    label: String?,
    suggestions: List<String>,
    selectedText: String,
    onValueChange: (value: String, index: Int) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    DefaultExpandableDropdown(
        modifier = modifier,
        label = label,
        suggestions = suggestions,
        selectedText = selectedText,
        onValueChange = onValueChange,
        expanded = expanded,
        onExpand = { expanded = it }
    )
}

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
    onDone: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    validField: Boolean = true,
    textAlign: TextAlign = TextAlign.End,
    alignment: Alignment = Alignment.BottomEnd,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    var hasFocus by remember { mutableStateOf(false) }
    val lineWidth = if (hasFocus) 2.dp else 1.dp
    val lineColor = if (validField) {
        if (hasFocus) MaterialTheme.colors.onSurface else Color.LightGray
    } else {
        SemanticError
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = lineWidth.value * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    lineColor,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = LocalTextStyle.current.copy(
                textAlign = textAlign,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onSurface,
                fontSize = fontSize
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            keyboardActions = KeyboardActions(
                onDone = { onDone() }
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = alignment
                ) {
                    if (value.isEmpty()) {
                        BodyText(
                            text = hint,
                            textAlign = textAlign,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray,
                            fontSize = fontSize
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    hasFocus = it.hasFocus
                },
        )
    }
}
