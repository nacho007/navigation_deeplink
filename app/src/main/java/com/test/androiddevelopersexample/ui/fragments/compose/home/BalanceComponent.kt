package com.test.androiddevelopersexample.ui.fragments.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey100
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.buttons.DefaultTextButton
import com.test.androiddevelopersexample.ui.fragments.compose.commons.cards.DefaultCardView
import com.test.androiddevelopersexample.ui.fragments.compose.commons.iconColorTint
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText

@Composable
fun BalanceComponent(
    modifier: Modifier = Modifier,
    walletBalance: List<Balance>,
    cryptoBalance: Balance? = null
) {
    DefaultCardView(
        modifier = modifier,
        color = Grey100,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            cryptoBalance?.let {
                WalletBalance(
                    modifier = Modifier.weight(1f),
                    walletBalance = walletBalance
                )
                CryptoBalance(
                    modifier = Modifier.weight(1f),
                    cryptoBalance = cryptoBalance
                )
            } ?: run {
                WalletBalanceFullSpan(
                    walletBalance = walletBalance
                )
            }
        }
    }
}

@Composable
private fun WalletBalanceFullSpan(
    modifier: Modifier = Modifier,
    walletBalance: List<Balance>
) {
    DefaultCardView(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyText(
                    modifier = Modifier
                        .weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    text = "My balance"
                )
                AmountComponent(
                    modifier = Modifier.padding(end = if (walletBalance.size > 1) 0.dp else 8.dp),
                    walletBalance = walletBalance
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Add money",
                action = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun WalletBalance(
    modifier: Modifier = Modifier,
    walletBalance: List<Balance>
) {
    DefaultCardView(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column() {
            Spacer(modifier = Modifier.height(8.dp))
            BodyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontWeight = FontWeight.SemiBold,
                text = "My balance"
            )
            Spacer(modifier = Modifier.height(4.dp))
            AmountComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                walletBalance = walletBalance
            )
            Spacer(modifier = Modifier.height(8.dp))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Add money",
                action = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AmountComponent(
    modifier: Modifier = Modifier,
    walletBalance: List<Balance>
) {
    val formattedBalances = walletBalance.map {
        it.currency.plus(" ".plus(it.amount))
    }
    val selectedValue = rememberSaveable { mutableStateOf(formattedBalances[0]) }
    if (walletBalance.size > 1) {
        DropdownCurrencies(
            modifier = modifier,
            balances = formattedBalances,
            selectedValue = selectedValue.value,
            onValueChange = {
                selectedValue.value = it
            }
        )
    } else {
        BodyText(
            modifier = modifier,
            text = selectedValue.value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun DropdownCurrencies(
    modifier: Modifier = Modifier,
    balances: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        val expanded = rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .clickable { expanded.value = expanded.value.not() },
        ) {
            BodyText(
                text = selectedValue,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.svg_arrow_down),
                contentDescription = "icon",
                tint = iconColorTint()
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
        ) {
            balances.forEach { label ->
                DropdownMenuItem(onClick = {
                    onValueChange(label)
                    expanded.value = false
                }) {
                    BodyText(text = label)
                }
            }
        }
    }
}

@Composable
private fun CryptoBalance(
    modifier: Modifier = Modifier,
    cryptoBalance: Balance
) {
    DefaultCardView(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column() {
            Spacer(modifier = Modifier.height(8.dp))
            BodyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontWeight = FontWeight.SemiBold,
                text = "Crypto"
            )
            Spacer(modifier = Modifier.height(4.dp))
            BodyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = cryptoBalance.currency.plus(" ".plus(cryptoBalance.amount)),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            DefaultTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Buy crypto",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

data class Balance(
    val currency: String,
    val amount: String,
)

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
private fun BalanceComponentFullSpanPreview() {
    AstroPayTheme {
        Surface {
            BalanceComponent(
                walletBalance = listOf(Balance("BRL", "10000"), Balance("USD", "50"))
            )
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
private fun BalanceComponentPreview() {
    AstroPayTheme {
        Surface {
            BalanceComponent(
                walletBalance = listOf(Balance("BRL", "10000"), Balance("USD", "50")),
                cryptoBalance = Balance("BRL", "1000")
            )
        }
    }
}