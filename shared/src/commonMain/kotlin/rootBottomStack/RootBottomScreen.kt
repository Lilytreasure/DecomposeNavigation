package rootBottomStack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

@Composable
fun RootBottomScreen(component: RootBottomComponent, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            /* do something */
                            component.openHome()
                        }) {
                            Icon(
                                Icons.Outlined.Home,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openFeeds()
                        }) {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openMessage()
                        }) {
                            Icon(
                                Icons.Outlined.ShoppingBag,
                                contentDescription = "Localized description",
                            )
                        }
                        IconButton(onClick = {
                            /* do something */
                            component.openNotifications()
                        }) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Localized description"
                            )
                        }

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