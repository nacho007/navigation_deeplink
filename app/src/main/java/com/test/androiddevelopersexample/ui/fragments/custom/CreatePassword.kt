package com.test.androiddevelopersexample.ui.fragments.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreatePassword(
    password: String,
    confirmPassword: String,
    onPasswordChange: (pass: String) -> Unit,
    onConfirmPasswordChange: (pass: String) -> Unit,
    isValidPassword: Boolean,
    showDifferentPasswordError: Boolean
) {
    Column {
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            hasError = isValidPassword.not() && password.isNotEmpty()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(
            visible = password.isNotEmpty(),
            enter = fadeIn()
        ) {
            PasswordStrength(password)
            Spacer(modifier = Modifier.height(16.dp))
        }
        RepeatPasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            hasError = showDifferentPasswordError
        )
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    labelText: String = "Ingresa tu contraseña",
    hasError: Boolean
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }

    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.hasFocus.not() && value.isNotEmpty()) {
//                        onValueChange(value)
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelText) },
            singleLine = true,
            isError = hasError,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (showPassword.value) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        icon,
                        contentDescription = "Visibility"
                    )
                }
            }
        )
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
            text = "Debe contener de 8 a 20 caracteres",
            fontSize = 12.sp,
            color = if (hasError) Color.Red else Color.Black
        )
    }
}

@Composable
private fun RepeatPasswordTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    labelText: String = "Repite tu contraseña",
    hasError: Boolean = false
) {
    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                onValueChange(value)
            },
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        singleLine = true,
        isError = hasError,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    icon,
                    contentDescription = "Visibility"
                )
            }
        },
    )
}

@Composable
private fun PasswordStrength(pass: String) {
    val strength = calculateStrength(pass)
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                backgroundColor = Color.LightGray,
                color = strength.color,
                progress = strength.percentage
            )
        }
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = strength.text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

private fun calculateStrength(password: String): StrengthLevel {
    var sawDigit = false
    var sawSpecial = false


    for (element in password) {
        if (!sawSpecial && !Character.isLetterOrDigit(element)) {
            sawSpecial = true
        } else {
            if (!sawDigit && Character.isDigit(element)) {
                sawDigit = true
            }
        }
    }

    return when {
        password.length > 11 && sawDigit && sawSpecial.not() -> StrengthLevel.MEDIUM
        password.length > 14 && sawDigit && sawSpecial -> StrengthLevel.STRONG
        else -> StrengthLevel.WEAK
    }
}

enum class StrengthLevel(val text: String, val color: Color, val percentage: Float) {
    WEAK("Weak", Color.Red, .33f),
    MEDIUM("Medium", Color.Yellow, .66f),
    STRONG("Strong", Color.Green, 1f)
}