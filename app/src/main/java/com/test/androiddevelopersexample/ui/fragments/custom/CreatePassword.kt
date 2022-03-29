package com.test.androiddevelopersexample.ui.fragments.custom

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
            fontSize = 10.sp,
            color = if (hasError) Color.Red else Color.Black
        )
    }
}

private fun isValidPassword(password: String): Boolean {
    return password.length in 8..20 || password.isEmpty()
}

@Composable
private fun RepeatPasswordTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    labelText: String = "Repite tu contraseña",
    hasError: Boolean = false,
//    validate: (isValid: Boolean) -> Unit
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