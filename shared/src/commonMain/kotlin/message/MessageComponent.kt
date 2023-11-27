package message

import platform.LoadFiles

interface MessageComponent {
    val loadFiles: LoadFiles

    fun onUpdateGreetingText()
    fun onBackClicked()


}