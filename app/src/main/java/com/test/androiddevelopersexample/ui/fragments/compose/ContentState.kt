package com.test.androiddevelopersexample.ui.fragments.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.ui.base.Intention

@Composable
fun ContentState(
    state: StateType,
    lastIntention: Intention = null,
    content: @Composable () -> Unit
): Boolean = when (state) {
    StateType.EMPTY -> false

    StateType.LOAD,
    StateType.LOAD_LIGHT -> {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        false
    }

    StateType.NETWORK_ERROR -> {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // LottieLoader(lottieResource = "astronaut_light_theme.json")

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Internet Error",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            lastIntention?.let { lastIntention ->
                Button(onClick = { lastIntention() }) {
                    Text(text = "Refresh")
                }
            }
        }
        false
    }

    StateType.HIDE,
    StateType.NONE -> {
        content()
        true
    }
}

enum class StateType {
    EMPTY, LOAD, LOAD_LIGHT, HIDE, NETWORK_ERROR, NONE
}