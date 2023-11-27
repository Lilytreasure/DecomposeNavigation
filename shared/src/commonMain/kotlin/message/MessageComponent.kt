package message

import platform.PlatformSpecific

interface MessageComponent {
    val loadFiles: PlatformSpecific

    fun onUpdateGreetingText()
    fun onBackClicked()


}