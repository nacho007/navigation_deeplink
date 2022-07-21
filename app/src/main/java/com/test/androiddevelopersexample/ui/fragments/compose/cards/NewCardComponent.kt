package com.test.androiddevelopersexample.ui.fragments.compose.cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R

@Composable
@Preview
fun NewCardComponent() {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 24f
        textAlign = android.graphics.Paint.Align.CENTER
        color = android.graphics.Color.RED
    }

    val text = LocalContext.current.getString(R.string.tab_new_card)

    Canvas(
        modifier = Modifier
            .size(100.dp, 100.dp)
            .padding(4.dp),
    ){
        drawRoundRect(color = Color.Red, style = stroke)
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                text,
                size.width / 2,
                size.height / 2,
                textPaint
            )
        }
    }
}