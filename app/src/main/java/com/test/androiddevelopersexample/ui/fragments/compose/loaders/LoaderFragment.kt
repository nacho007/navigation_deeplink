package com.test.androiddevelopersexample.ui.fragments.compose.loaders

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentComposeBinding
import com.test.androiddevelopersexample.theme.AstroPayTheme
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.compose.ComposeViewModel
import com.test.androiddevelopersexample.ui.fragments.compose.commons.texts.BodyText
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.AstroToolBar
import com.test.androiddevelopersexample.ui.fragments.compose.commons.toolbar.IconNavigationBack
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.ContentState
import com.test.androiddevelopersexample.ui.fragments.compose.commons.view_state.Type
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ignaciodeandreisdenis on 18/8/22.
 */
class LoaderFragment : BaseFragment<FragmentComposeBinding>(FragmentComposeBinding::inflate) {

    override var screenTag = "LoaderFragment"

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
        ContentState(
            state = screenState.loadState,
            lastIntention = { viewModel.lastIntention() },
            toolbar = {
                AstroToolBar(
                    title = stringResource(id = R.string.mobile_transfer),
                ) {
                    IconNavigationBack {
                        findNavController().navigateUp()
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(32.dp))

                    BodyText(text = "My Text")

                    BodyText(text = "My Text2")

                    BodyText(text = "My Text3")

                    Button(onClick = {
                        viewModel.onLoadBlack()
                    }) {
                        BodyText(text = "Load")
                    }

                    Button(onClick = {
                        viewModel.onEmpty()
                    }) {
                        BodyText(text = "Empty")
                    }

                    Button(onClick = {
                        viewModel.onNetworkError()
                    }) {
                        BodyText(text = "Network Error")
                    }
                }
            },
            floatingButton = {}
        )
    }

    override val fragmentName: String
        get() = "LoaderFragment"
    override val screenName: String
        get() = "LoaderFragment"

    @Composable
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    private fun ComposeFragmentPreview() {
        AstroPayTheme {
            Screen(screenState = ComposeViewModel.ViewState(loadState = Type.SHOW_CONTENT))
        }
    }
}