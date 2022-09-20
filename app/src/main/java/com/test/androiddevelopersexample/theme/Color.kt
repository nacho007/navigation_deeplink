package com.test.androiddevelopersexample.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val PrimaryVariant = Color(0x85FF0000)
val PrimaryVariantDark = Color(0x85039688)

val Aqua = Color(0xFF039688)

val GlobalPrimary100 = Color(0xfffff8f8)
val GlobalPrimary200 = Color(0xffffe2e2)
val GlobalPrimary300 = Color(0xffffbfbf)
val GlobalPrimary400 = Color(0xffe60022)
val GlobalPrimary500 = Color(0xffe82121)
val GlobalPrimary600 = Color(0xffcc0000)

val Grey25 = Color(0xfffcfcfd)
val Grey50 = Color(0xfff9fafb)
val Grey100 = Color(0xfff2f4f7)
val Grey200 = Color(0xffeaecf0)
val Grey300 = Color(0xffd0d5dd)
val Grey400 = Color(0xff98A2B3)
val Grey500 = Color(0xff667085)
val Grey600 = Color(0xff475467)
val Grey700 = Color(0xff344054)
val Grey800 = Color(0xff1D2939)
val Grey900 = Color(0xff101828)

val Indigo25 = Color(0xfff5faff)
val Indigo50 = Color(0xffeff8ff)
val Indigo100 = Color(0xffd1e9ff)
val Indigo600 = Color(0xff1570ef)
val Indigo700 = Color(0xff175CD3)
val Indigo800 = Color(0xff1849A9)
val Indigo900 = Color(0xff194185)

val Yellow25 = Color(0xfffffcf5)
val Yellow50 = Color(0xfffffaeb)
val Yellow300 = Color(0xfffec84b)
val Yellow400 = Color(0xfffdb022)
val Yellow700 = Color(0xffb54708)

val Green25 = Color(0xfff6fef9)
val Green50 = Color(0xffd7fde6)
val Green300 = Color(0xff6CE9A6)
val Green400 = Color(0xff32D583)
val Green700 = Color(0xff027A48)
val Green800 = Color(0xff05603A)
val Green900 = Color(0xff054f31)

val Red100 = Color(0xffffa8a8)
val Red200 = Color(0xffff6767)
val Red600 = Color(0xffcc0000)
val Red700 = Color(0xff990000)

@Composable
fun textColor() = if (isSystemInDarkTheme()) Color.White else Grey800

@Composable
fun cardBackground() = if (isSystemInDarkTheme()) Grey800 else Color.White

@Composable
fun secondaryButtonColor() = if (isSystemInDarkTheme()) Grey800 else Grey25

@Composable
fun dialogBackgroundColor() = if (isSystemInDarkTheme()) Grey800 else Color.White

@Composable
fun cardSecondaryBackground() = if (isSystemInDarkTheme()) Grey800 else Grey100

val Colors.DebitCardGradientBackground: Color
    get() = if(isLight) Color(0xFFFFA500) else Color(0xFF013220)