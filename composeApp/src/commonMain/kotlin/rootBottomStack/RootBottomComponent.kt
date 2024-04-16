package rootBottomStack

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import buy.BuyComponent
import about.AboutComponent
import notifications.NotificationComponent
import home.HomeComponent


interface RootBottomComponent {
    val childStackBottom: Value<ChildStack<*, ChildBottom>>
    fun openHome()
    fun openFeeds()
    fun openMessage()
    fun openNotifications()

    sealed class ChildBottom {
        class WelcomeChild(val component: HomeComponent) : ChildBottom()
        class FeedsChild(val component: BuyComponent) : ChildBottom()
        class MessageChild(val component: AboutComponent) : ChildBottom()
        class NotificationsChild(val component: NotificationComponent) : ChildBottom()
    }


}