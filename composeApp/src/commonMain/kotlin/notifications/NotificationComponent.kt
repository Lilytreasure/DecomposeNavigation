package notifications

import PlatformSpecific


interface NotificationComponent {
    val platformSpecific: PlatformSpecific
    fun onUpdateGreetingText()
    fun onBackClicked()


}