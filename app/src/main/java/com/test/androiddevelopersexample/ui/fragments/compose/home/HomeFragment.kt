package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.H4Title
import com.test.androiddevelopersexample.ui.utils.navigate

/**
 * Created by ignaciodeandreisdenis on 20/7/22.
 */
class HomeFragment :
    BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    private val vouchers = listOf(
        "R$ 1000",
        "R$ 30",
        "R$ 5000",
        "R$ 58",
        "R$ 1000",
        "R$ 30",
        "R$ 5000",
        "R$ 58",
        "R$ 1000",
        "R$ 30",
        "R$ 5000",
        "R$ 58"
    )

    private val transactions = listOf(
        "R$ 200",
        "BTC 0.03",
        "USD 45",
        "R$ 1000"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                AstroPayTheme {
                    Screen()
                }
            }
        }
    }

    @Composable
    fun Screen() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                H4Title(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Home"
                )

                Spacer(modifier = Modifier.height(16.dp))

                MyVouchersComponent(
                    vouchers = vouchers,
                    newVoucher = { navigate(R.id.open_home) },
                    seAllVoucher = { navigate(R.id.open_home) },
                    openVoucher = {

                    }
                )
                Spacer(modifier = Modifier.height(32.dp))

                MyVouchersComponent(
                    vouchers = emptyList(),
                    newVoucher = { navigate(R.id.open_home) },
                    seAllVoucher = { navigate(R.id.open_home) },
                    openVoucher = {

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TransactionsComponent(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp),
                    transactions = transactions,
                    seAllTransactions = { }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TransactionsComponent(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp),
                    transactions = emptyList(),
                    seAllTransactions = { }
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

    @Composable
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        locale = "en"
    )
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        locale = "en"
    )
    private fun HomePreview() {
        AstroPayTheme {
            Screen()
        }
    }
}
