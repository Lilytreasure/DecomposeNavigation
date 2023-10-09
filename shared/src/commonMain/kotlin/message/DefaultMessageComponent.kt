package message

import com.arkivanov.decompose.ComponentContext

class DefaultMessageComponent(
    componentContext: ComponentContext,
    private val onShowWelcome: () -> Unit,
) : MessageComponent, ComponentContext by componentContext {


    override fun onUpdateGreetingText() {
        TODO("Not yet implemented")
    }

    override fun onBackClicked() {
        TODO("Not yet implemented")
    }
}