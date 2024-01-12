package rootBottomStack

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import feeds.BuyComponent
import message.AboutComponent
import notifications.NotificationComponent
import welcome.WelcomeComponent


interface RootBottomComponent {
    val childStackBottom: Value<ChildStack<*, ChildBottom>>
    fun openHome()
    fun openFeeds()
    fun openMessage()
    fun openNotifications()

    sealed class ChildBottom {
        class WelcomeChild(val component: WelcomeComponent) : ChildBottom()
        class FeedsChild(val component: BuyComponent) : ChildBottom()
        class MessageChild(val component: AboutComponent) : ChildBottom()
        class NotificationsChild(val component: NotificationComponent) : ChildBottom()
    }


}