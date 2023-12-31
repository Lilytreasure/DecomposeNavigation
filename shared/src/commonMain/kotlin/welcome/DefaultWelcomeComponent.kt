package welcome

import com.arkivanov.decompose.ComponentContext


class DefaultWelcomeComponent(
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit,
) : WelcomeComponent, ComponentContext by componentContext {
    override fun onUpdateGreetingText() {
    }
    override fun onBackClicked() {
        onFinished()
    }
}