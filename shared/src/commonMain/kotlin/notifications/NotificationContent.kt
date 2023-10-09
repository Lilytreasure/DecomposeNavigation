package notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationContent(component: NotificationComponent, modifier: Modifier = Modifier) {
    Column() {
        Text(text = "This  is  the Notification  Screen")

    }
}