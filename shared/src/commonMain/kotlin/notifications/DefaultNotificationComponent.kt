package notifications

import com.arkivanov.decompose.ComponentContext

class DefaultNotificationComponent(
    componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit,
) : NotificationComponent, ComponentContext by componentContext {


    override fun onUpdateGreetingText() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}