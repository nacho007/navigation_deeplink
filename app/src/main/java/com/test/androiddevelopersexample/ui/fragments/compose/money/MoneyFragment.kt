package com.test.androiddevelopersexample.ui.fragments.compose.money

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.custom.ContentState
import com.test.androiddevelopersexample.ui.custom.Type
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.IconNavigationBack
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoneyFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "MoneyFragment"

    override var showBottomNavigation: Boolean = true

    private val viewModel: ComposeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            composeView.setContent {
                AstroPayTheme {
                    val screenState by viewModel.stateLiveData.observeAsState(initial = ComposeViewModel.ViewState())
                    Screen(screenState = screenState)
                }
            }
        }
        viewModel.loadData()
    }


    @Composable
    private fun Screen(screenState: ComposeViewModel.ViewState) {
        Scaffold(
            topBar = {
                AstroToolBar(title = "Toolbar") {
                    IconNavigationBack {
                        findNavController().popBackStack()
                    }
                }
            },
            content = {
                ContentState(
                    state = screenState.loadState,
                    lastIntention = { }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AstroText(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Este es mi texto"
                        )
                    }

                }
            }
        )
    }

    @Composable
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        locale = "es"
    )
    @Preview(
        device = Devices.PIXEL_4,
        uiMode = Configuration.UI_MODE_NIGHT_NO,
        locale = "es"
    )
    private fun ComposeFragmentPreview() {
        AstroPayTheme {
            Screen(screenState = ComposeViewModel.ViewState(loadState = Type.HIDE))
        }
    }
}
