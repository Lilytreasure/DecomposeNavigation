package home

import com.arkivanov.decompose.ComponentContext


class DefaultHomeComponent(
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit,
) : HomeComponent, ComponentContext by componentContext {
    override fun onUpdateGreetingText() {
    }
    override fun onBackClicked() {
        onFinished()
    }
}