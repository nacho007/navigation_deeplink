package com.test.androiddevelopersexample.ui.fragments.more

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
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.test.androiddevelopersexample.R
import com.test.androiddevelopersexample.constants.FIREBASE_TOKEN
import com.test.androiddevelopersexample.databinding.FragmentMoreBinding
import com.test.androiddevelopersexample.ui.activities.NavigationActivity
import com.test.androiddevelopersexample.ui.fragments.base.BaseFragment
import com.test.androiddevelopersexample.ui.fragments.custom.IconButton
import com.test.androiddevelopersexample.ui.utils.DeepLinkUtils.PUSH_LOYALTY
import com.test.androiddevelopersexample.ui.utils.Messaging
import com.test.androiddevelopersexample.ui.utils.PushNotificationUtils
import com.test.androiddevelopersexample.ui.utils.navigate
import com.test.androiddevelopersexample.ui.utils.showToast
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.coroutines.suspendCoroutine

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
                            NotificationLoyaltyButton()
                            NotificationArticleButton()
                            BottomSheetButton()
                            HelpCenterButton()
                            LogoutButton()
                            SwipeButton()
                            OnBoardingComposeButton()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

        }

    }

    @Composable
    private fun OnBoardingComposeButton() {
        IconButton(text = R.string.onboarding_compose, action = {
            findNavController().navigate(R.id.action_moreFragment_to_OnBoardingFragment)
        })
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
    private fun NotificationLoyaltyButton() {
        val coroutineScope = rememberCoroutineScope()

        IconButton(text = R.string.generate_push_notification_loyalty, action = {
            coroutineScope.launch {
                invoke(
                    NotificationType.Loyalty(
                        text = "",
                        title = "Loyalty title",
                        type = PUSH_LOYALTY,
                        body = "Loyalty Body"
                    )
                )
            }
        })
    }

    @Composable
    private fun NotificationArticleButton() {
        IconButton(text = R.string.generate_push_notification_article, action = {


            val executor: ScheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor()
            executor.schedule({
                PushNotificationUtils.createTradePushArticle(
                    requireContext(),
                    "Article title",
                    "Article Body"
                )
            }, 1500.toLong(), TimeUnit.MILLISECONDS)


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


    suspend operator fun invoke(type: NotificationType) {
        val pushNotificationData = when (type) {
            is NotificationType.Loyalty -> {
                PushNotification.Message.Data(
                    type = type.type
                )
            }
        }

        val pushNotification = generatePushNotification(type, pushNotificationData)
        scheduleMessage(JsonParser().parse(Gson().toJson(pushNotification)).asJsonObject)
    }

    private fun generatePushNotification(
        type: NotificationType,
        data: PushNotification.Message.Data?
    ): PushNotification {
        return PushNotification(
            PushNotification.Message(
                requireActivity().getSharedPreferences(
                    getString(R.string.preferences), Context.MODE_PRIVATE
                ).getString(FIREBASE_TOKEN, ""),
                PushNotification.Message.Notification(type.title, type.body),
                data
            )
        )
    }

    private fun scheduleMessage(notification: JsonObject) {
        val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        executor.schedule({
            Messaging.sendMessage(requireContext(), notification)
        }, PUSH_DEV_OPT_DELAY, TimeUnit.MILLISECONDS)
        showToast(PUSH_TIME_TOAST_TEXT, binding.root)
    }

    sealed class NotificationType {
        abstract val text: String
        abstract val title: String
        abstract val body: String
        abstract val type: String

        data class Loyalty(
            override val text: String = "Text",
            override val title: String = "Loyalty title",
            override val body: String = "Loyalty body",
            override val type: String = "LOYALTY"
        ) : NotificationType()
    }

    data class PushNotification(
        val message: Message,
    ) {
        data class Message(
            val token: String?,
            val notification: Notification,
            val data: Data?
        ) {
            data class Notification(
                val title: String,
                val body: String
            )

            data class Data(
                val type: String,
            )
        }
    }

    companion object {
        private const val SCREEN_NAME = "DEVELOPER_OPTIONS"
        private const val PUSH_DEV_OPT_DELAY = 3000L
        private const val PUSH_TIME_TOAST_TEXT = "Push will be sent in 3 seconds"
        private const val DIALOG = 103
    }
}
