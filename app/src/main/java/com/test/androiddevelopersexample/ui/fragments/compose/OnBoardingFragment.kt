package com.test.androiddevelopersexample.ui.fragments.compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter.MdcTheme
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.custom.CodeValidation
import com.test.androiddevelopersexample.ui.fragments.custom.CreatePassword
import com.test.androiddevelopersexample.ui.fragments.custom.OnBoardingProgressBar
import com.test.androiddevelopersexample.ui.fragments.custom.PhoneNumberTextField
import org.koin.android.viewmodel.ext.android.viewModel

class OnBoardingFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "OnBoardingFragment"

    private val viewModel: OnboardingViewModel by viewModel()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            composeView.setContent {
                MdcTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colorResource(id = R.color.color_view_background))
                    ) {
                        val uiState = viewModel.stateLiveData.observeAsState(viewModel.state)

                        ContentState(
                            state = uiState.value.state,
                            lastIntention = viewModel.lastIntention
                        ) {
                            CodeScreen()
                        }
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

        val isValidPassword = remember { mutableStateOf(false) }
        var showDifferentPasswordError by remember { mutableStateOf(false) }
        val canEnableButton = remember { mutableStateOf(false) }
        val password = remember { mutableStateOf("") }
        val confirmPassword = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding))
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))
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
            PhoneNumberTextField(countryUrl = viewModel.getCountryUrl())
            Spacer(modifier = Modifier.height(16.dp))
            TermsCheckBox(context = context)

            Spacer(modifier = Modifier.height(16.dp))
            CreatePassword(
                password = password.value,
                confirmPassword = confirmPassword.value,
                onPasswordChange = {
                    password.value = it
                    showDifferentPasswordError = false
                    isValidPassword.value = it.length in 8..20
                    canEnableButton.value =
                        isValidPassword.value && confirmPassword.value.isNotEmpty()
                },
                onConfirmPasswordChange = {
                    confirmPassword.value = it
                    canEnableButton.value = isValidPassword.value && it.isNotEmpty()
                    showDifferentPasswordError = false
                },
                isValidPassword = isValidPassword.value,
                showDifferentPasswordError = showDifferentPasswordError
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showDifferentPasswordError = password.value != confirmPassword.value
                    if (showDifferentPasswordError.not()) {
                        viewModel.emulateLoading()
                    }
                },
                enabled = canEnableButton.value
            ) {
                Text(text = "CREATE PASSWORD")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.emulateNetworkError()
                }
            ) {
                Text(text = "NETWORK ERROR")
            }
        }
    }

    @Composable
    fun TermsCheckBox(context: Context) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isChecked = remember { mutableStateOf(false) }

            Checkbox(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = it }
            )
            TextTermsAndConditions(context) {
                isChecked.value = isChecked.value.not()
            }
        }
    }

    @Composable
    private fun TextTermsAndConditions(
        context: Context,
        onLabelClicked: () -> Unit
    ) {

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
                    else -> onLabelClicked()
                }
            }
        )
    }

    private fun mToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}