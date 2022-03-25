package com.test.androiddevelopersexample.ui.fragments.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeValidation(
    onCodeCompleted: (Boolean, String) -> Unit,
    hasError: Boolean = false
) {
    val passcodeLength = 5
    val codeArray = remember {
        Array(passcodeLength) {
            mutableStateOf(TextFieldValue(text = ""))
        }
    }
    val focusRequesters = remember {
        (0 until passcodeLength).map { FocusRequester() }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(passcodeLength) { index ->
            Column(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .border(
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (hasError) 46.dp else 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .onPreviewKeyEvent {
                                if (it.type == KeyEventType.KeyDown && it.key == Key.Backspace &&
                                    index != 0 && codeArray[index].value.text.isEmpty()
                                ) {
                                    codeArray[index - 1].value =
                                        codeArray[index - 1].value.copy(text = "")
                                    focusRequesters[index - 1].requestFocus()
                                }
                                false
                            }
                            .focusRequester(focusRequesters[index]),
                        value = codeArray[index].value,
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = { newTextFieldValue ->
                            val newString = newTextFieldValue.text
                            if (newString.isDigitsOnly() && codeArray[index].value.text != newString) {
                                codeArray[index].value =
                                    if (newString.length > 1) {
                                        newTextFieldValue.copy(text = newString.takeLast(1))
                                    } else newTextFieldValue
                                if (newString.isNotEmpty()) {
                                    val nextPosition = index + 1
                                    if (nextPosition != passcodeLength) {
                                        focusRequesters[nextPosition].requestFocus()
                                    }
                                }
                            }
                            val isCodeComplete =
                                codeArray.indices.firstOrNull {
                                    codeArray[it].value.text == ""
                                } == null

                            onCodeCompleted(
                                isCodeComplete,
                                codeArray.joinToString(separator = "") {
                                    it.value.text
                                }
                            )
                        }
                    )
                }
                if (hasError) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color.Red)
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        focusRequesters[0].requestFocus()
    }
}