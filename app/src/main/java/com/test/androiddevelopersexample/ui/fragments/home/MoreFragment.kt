package com.test.androiddevelopersexample.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.databinding.FragmentMoreBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.custom.IconButton
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils
import com.test.androiddevelopersexample.ui.utils.navigate
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by ignaciodeandreisdenis on 4/7/21.
 */
class MoreFragment : BaseFragment<FragmentMoreBinding>(FragmentMoreBinding::inflate) {

    override var screenTag = "MoreFragment"

    override var showBottomNavigation = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            buttonsContainer.setContent {
                MaterialTheme {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomComponentButton()
                            CoilButton()
                            BadgesButton()
                            AstrocoinsButton()
                            NotificationButton()
                            BottomSheetButton()
                            HelpCenterButton()
                            LogoutButton()
                            SwipeButton()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

        }

    }

    @Composable
    private fun SwipeButton() {
        IconButton(text = R.string.swipe, action = {
            navigate(R.id.action_moreFragment_to_swipeFragment)
        })
    }

    @Composable
    private fun LogoutButton() {
        IconButton(text = R.string.logout, action = {
            val sharedPref = activity?.getSharedPreferences(
                getString(R.string.preferences), Context.MODE_PRIVATE
            )

            sharedPref?.edit()?.putBoolean(getString(R.string.is_logged), false)
                ?.apply()

            (activity as NavigationActivity).showBottomNavigationMenu(false)
            (activity as NavigationActivity).logOut()
        })
    }

    @Composable
    private fun HelpCenterButton() {
        IconButton(text = R.string.help_center, action = {
            navigate(R.id.action_moreFragment_to_fragmentHelpCenter)
        })
    }

    @Composable
    private fun BottomSheetButton() {
        IconButton(text = R.string.bottom_sheet, action = {
            navigate(R.id.action_moreFragment_to_phoneBottomSheet)
        })
    }

    @Composable
    private fun NotificationButton() {
        IconButton(text = R.string.generate_notification, action = {
            val executor: ScheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor()
            executor.schedule({
                DeepLinkUtils.createNotification(
                    requireContext(),
                    "Title",
                    "Body"
                )
            }, 3000.toLong(), TimeUnit.MILLISECONDS)
        })
    }

    @Composable
    private fun AstrocoinsButton() {
        IconButton(text = R.string.astro_coins, action = {
            findNavController().navigate(R.id.action_moreFragment_to_astroCoinsFragment)
        })
    }

    @Composable
    private fun BadgesButton() {
        IconButton(
            text = R.string.generate_badges, action = {
                (activity as NavigationActivity).createBadges(
                    R.id.newCardFragment,
                    2
                )
                (activity as NavigationActivity).createBadges(
                    R.id.loyaltyFragment,
                    3
                )
            },
            enabled = false
        )
    }

    @Composable
    private fun CoilButton() {
        var isNew by remember { mutableStateOf(true) }
        IconButton(text = R.string.contacts_with_coil, isNew = isNew, action = {
            isNew = false
            findNavController().navigate(R.id.action_moreFragment_to_contactsFragment)
        })
    }

    @Composable
    private fun CustomComponentButton() {
        IconButton(text = R.string.custom_component, action = {
            findNavController().navigate(
                R.id.action_moreFragment_to_fragmentCustomComponent
            )
        })
    }
}
