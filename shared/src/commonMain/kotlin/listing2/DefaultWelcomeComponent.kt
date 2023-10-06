package listing2

import com.arkivanov.decompose.ComponentContext
import listing2.WelcomeComponent


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