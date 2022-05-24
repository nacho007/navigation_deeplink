package com.test.androiddevelopersexample.ui.fragments.compose

import android.os.Bundle
import android.view.View
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment

/**
 * Created by ignaciodeandreisdenis on 24/5/22.
 */
@ExperimentalFoundationApi
class ComposeFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "CardsListFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    Screen()
                }
            }
        }
    }


    @Composable
    private fun Screen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.color_black_opacity_75)),
            contentAlignment = Alignment.Center
        ) {
            CustomDialog(
                onConfirm = {},
                onCancel = { } ,
                promoCode = "PromoCode",
                onPromoCodeChange = { } ,
                error = null,
                showInput = false,
                onPromoVisibility = {}
            )
        }
    }

    @Composable
    private fun CustomDialog(
        showInput: Boolean,
        promoCode: String,
        onPromoCodeChange: (code: String) -> Unit,
        error: String?,
        onConfirm: () -> Unit,
        onPromoVisibility: (show: Boolean) -> Unit,
        onCancel: () -> Unit
    ) {
        Surface(
            modifier = Modifier
                .padding(32.dp)
                .wrapContentWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.mobile_on_boarding_promo_code_title),
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                if (showInput) {
                    AstroOutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = promoCode,
                        onValueChange = {
                            onPromoCodeChange(it)
                        },
                        label = { Text(text = stringResource(R.string.mobile_on_boarding_promo_code_enter)) },
                        singleLine = true,
                        isError = error != null
                    )
                    if (error != null) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            text = error,
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.error
                        )
                    }
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        text = stringResource(R.string.mobile_on_boarding_yes_apply_promo_code),
                        action = { onConfirm() }
                    )
                } else {
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        text = stringResource(R.string.mobile_on_boarding_yes_apply_promo_code),
                        action = { onPromoVisibility(showInput.not()) }
                    )
                }

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onCancel
                ) {
                    Text(
                        text = stringResource(id = R.string.mobile_on_boarding_continue_without_promo_code),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}