package com.test.androiddevelopersexample.ui.fragments.compose.commons.texts

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun String.setSubstringWithStyle(
    substring: String,
    style: SpanStyle
): AnnotatedString {
    val index = this.uppercase().indexOf(substring.uppercase())
    val text = this

    return if (index != -1) {
        val textStart = text.substring(0, index)
        val searchFinal = text.substring(index, index + substring.length)
        val textEnd = text.substring(index + substring.length)
        buildAnnotatedString {
            append(textStart)
            withStyle(style = style) {
                append(searchFinal)
            }
            append(textEnd)
        }
    } else buildAnnotatedString {
        append(text)
    }
}
