package com.test.androiddevelopersexample.ui.fragments.compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.custom.CodeValidation
import com.test.androiddevelopersexample.ui.fragments.custom.OnBoardingProgressBar

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
        val context = LocalContext.current

        val pinCode = remember { mutableStateOf("") }
        val hasError = remember { mutableStateOf(false) }
        val isCodeCompleted = remember { mutableStateOf(false) }

        val steps = 4
        val currentStep = remember { mutableStateOf(1) }

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            OnBoardingProgressBar(
                steps = steps,
                currentStep = currentStep.value
            )
            CodeValidation(
                onCodeCompleted = { valid, code ->
                    isCodeCompleted.value = valid
                    hasError.value = false
                    pinCode.value = code
                },
                hasError = hasError.value
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    hasError.value = pinCode.value != "11111"
                    currentStep.value++
                },
                enabled = isCodeCompleted.value
            ) {
                Text(text = "VALIDATE")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextTermsAndConditions(context = context)
        }
    }
}

@Composable
fun TextTermsAndConditions(context: Context) {

    val termsAndConditions = context.getString(R.string.mobile_terms_and_conditions)
    val privacyPolicy = context.getString(R.string.mobile_privacy_policy)
    val string = context.getString(
        R.string.mobile_terms_and_conditions_privacy_policy,
        termsAndConditions,
        privacyPolicy
    )

    val termsAndConditionsStartPosition = string.indexOf(termsAndConditions)
    val termsAndConditionsEndPosition =
        termsAndConditions.length + termsAndConditionsStartPosition
    val privacyPolicyStartPosition = string.indexOf(privacyPolicy)
    val privacyPolicyEndPosition = privacyPolicy.length + privacyPolicyStartPosition

    val annotatedString = AnnotatedString(
        text = string,
        spanStyles = listOf(
            AnnotatedString.Range(
                SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red),
                termsAndConditionsStartPosition,
                termsAndConditionsEndPosition
            ),
            AnnotatedString.Range(
                SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red),
                privacyPolicyStartPosition,
                privacyPolicyEndPosition
            )
        )
    )

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            when (offset) {
                in termsAndConditionsStartPosition..termsAndConditionsEndPosition -> {
                    Log.d("CLICKED", "Terms and Conditions")
                    mToast(context = context, text = "Terms and Conditions :: CLICKED")
                }
                in privacyPolicyStartPosition..privacyPolicyEndPosition -> {
                    Log.d("CLICKED", "Privacy Policy")
                    mToast(context = context, text = "Privacy Policy :: CLICKED")
                }
            }
        }
    )

}

private fun mToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}