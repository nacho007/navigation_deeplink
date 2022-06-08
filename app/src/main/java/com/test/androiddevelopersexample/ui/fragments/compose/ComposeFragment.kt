package com.test.androiddevelopersexample.ui.fragments.compose

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.ui.custom.ContentState
import com.test.androiddevelopersexample.ui.custom.Type
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 24/5/22.
 */
@ExperimentalFoundationApi
class ComposeFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "ComposeFragment"

    private val viewModel: ComposeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                MaterialTheme {
                    val screenState by viewModel.stateLiveData.observeAsState(initial = ComposeViewModel.ViewState())
                    Screen(screenState = screenState)
                }
            }
        }
        viewModel.loadData()
    }


    @Composable
    private fun Screen(screenState: ComposeViewModel.ViewState) {
        ContentState(
            state = screenState.state,
            lastIntention = { }
        ) {
            Box(
                modifier = Modifier
                    .background(colorResource(id = R.color.color_advise))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomDialog(
                    onConfirm = {

                    },
                    onCancel = { },
                    promoCode = "PromoCode",
                    onPromoCodeChange = { },
                    error = null,
                    showInput = true,
                    onPromoVisibility = {}
                )
            }
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

    @Composable
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    private fun ComposeFragmentPreview() {
        Screen(screenState = ComposeViewModel.ViewState(state = Type.HIDE))
    }
}