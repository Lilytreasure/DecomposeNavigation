package rootBottomStack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RssFeed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import feeds.FeedsContent
import message.MessageContent
import notifications.NotificationContent
import welcome.WelcomeContent

data class ScreensBottom(val name: String, val openScreen: () -> Unit, val isSelected: Boolean)

@Composable
fun RootBottomScreen(component: RootBottomComponent, modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val screens by remember {
        mutableStateOf(
            listOf(
                ScreensBottom("Home", component::openHome, false),
                ScreensBottom("Feeds", component::openFeeds, false),
                ScreensBottom("Shopping", component::openMessage, false),
                ScreensBottom("Notification", component::openNotifications, false)
            )
        )
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    screens.forEachIndexed { index, screensBottom ->
                        NavigationBarItem(
                            icon = {
                                when (screensBottom.name) {
                                    "Home" -> Icon(Icons.Outlined.Home, contentDescription = null)
                                    "Feeds" -> Icon(
                                        Icons.Outlined.RssFeed,
                                        contentDescription = null
                                    )

                                    "Shopping" -> Icon(
                                        Icons.Default.ShoppingCart,
                                        contentDescription = null
                                    )

                                    "Notification" -> Icon(
                                        Icons.Default.Notifications,
                                        contentDescription = null
                                    )
                                }
                            },
                            label = { Text(text = screensBottom.name) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem=index
                                screensBottom.openScreen()
                            },
                            colors =NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            )

        },
        content = { innerpadding ->
            Column(modifier = Modifier.padding(innerpadding)) {
                Children(
                    stack = component.childStackBottom,
                    modifier = modifier,
                    animation = stackAnimation(fade() + scale()),
                ) {
                    when (val child = it.instance) {
                        is RootBottomComponent.ChildBottom.WelcomeChild -> WelcomeContent(component = child.component)
                        is RootBottomComponent.ChildBottom.FeedsChild -> FeedsContent(component = child.component)
                        is RootBottomComponent.ChildBottom.MessageChild -> MessageContent(component = child.component)
                        is RootBottomComponent.ChildBottom.NotificationsChild -> NotificationContent(
                            component = child.component
                        )
                    }
                }
            }
        })


}