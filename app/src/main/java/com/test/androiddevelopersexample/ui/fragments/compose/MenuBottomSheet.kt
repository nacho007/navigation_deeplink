package com.test.androiddevelopersexample.ui.fragments.compose

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.theme.Grey300
import com.test.androiddevelopersexample.ui.fragments.BaseBottomSheet
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.utils.navigate


/**
 * Created by ignaciodeandreisdenis on 8/7/21.
 */
class MenuBottomSheet :
    BaseBottomSheet<FragmentComposeBinding>(FragmentComposeBinding::inflate, STATE_HALF_EXPANDED) {

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
    private fun Screen() {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                repeat(4) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    navigate(R.id.open_home)
                                    dismiss()
                                }
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.svg_arrow_right),
                                contentDescription = "icon"
                            )
                            BodyText(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp),
                                text = "Transfer",
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.svg_arrow_right),
                                contentDescription = "icon"
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 7.dp)
                                .height(1.dp)
                                .background(Grey300)
                        )
                    }
                }
            }
        }
    }

    @Composable
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    private fun ComposeFragmentPreview() {
        AstroPayTheme {
            Screen()
        }
    }
}
