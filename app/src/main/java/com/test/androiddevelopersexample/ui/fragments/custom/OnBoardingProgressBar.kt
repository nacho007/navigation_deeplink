package com.test.androiddevelopersexample.ui.fragments.custom

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingProgressBar(
    steps: Int,
    currentStep: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(80.dp)
                    .height(8.dp),
                backgroundColor = Color.LightGray,
                color = Color.Red,
                progress = calculateProgress(currentStep, steps)
            )
        }
        Text(
            modifier = Modifier
                .padding(2.dp),
            text = formatSteps(currentStep, steps),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun calculateProgress(current: Int, steps: Int): Float {
    return ((current.toFloat() / steps.toFloat()))
}

private fun formatSteps(current: Int, steps: Int): String {
    return if (current < steps) {
        "$current/$steps"
    } else {
        "$steps/$steps"
    }
}