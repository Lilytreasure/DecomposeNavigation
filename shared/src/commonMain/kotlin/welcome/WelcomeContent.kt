package welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WelcomeContent(component: WelcomeComponent, modifier: Modifier = Modifier) {

    Column() {

        Text("This is the first Screen")

    }
}