package com.test.androiddevelopersexample.ui.fragments.compose

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.custom.CodeValidation

/**
 * Created by ignaciodeandreisdenis on 4/11/21.
 */
class OnBoardingFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "OnBoardingFragment"

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            composeView.setContent {
                MaterialTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.color_view_background))
                    ) {
                        CodeScreen()
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    @Preview(showBackground = true)
    fun CodeScreen() {
        val pinCode = remember { mutableStateOf("") }
        val hasError = remember { mutableStateOf(false) }
        val isCodeCompleted = remember { mutableStateOf(false) }
        Column {
            CodeValidation(
                onCodeCompleted = { valid, code ->
                    isCodeCompleted.value = valid
                    hasError.value = false
                    pinCode.value = code
                },
                hasError = hasError.value
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    hasError.value = pinCode.value != "11111"
                },
                enabled = isCodeCompleted.value
            ) {
                Text(text = "VALIDATE")
            }
        }
    }
}
