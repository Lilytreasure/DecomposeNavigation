package notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(component: NotificationComponent, modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        SmallTopAppBar(title = { Text(text = "Native API illustration") })
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth(), onClick = {
                        component.platformSpecific.launchDialer("+2547897567")

                }) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Call Icon",
                            tint = Color.Green,
                            modifier = Modifier.padding(1.dp)
                        )
                        Text(text = "Call")
                    }
                }
            }
        }
    }
}