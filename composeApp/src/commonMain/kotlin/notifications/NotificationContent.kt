package notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent(component: NotificationComponent, modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Notify", fontSize = 15.sp) })
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth(), onClick = {
                    component.platformSpecific.launchDialer("2547897567")

                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = "Call Icon",
                            tint = Color.Green,
                            modifier = Modifier.padding(1.dp)
                        )
                        Text(text = "Call")
                    }
                }
                //FilesUpload
                component.platformSpecific.CameraView()

            }
        }
    }
}