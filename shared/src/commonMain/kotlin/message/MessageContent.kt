package message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import feeds.FilledTonalButtonExample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageContent(component: MessageComponent, modifier: Modifier = Modifier) {
    var showFilePicker by remember { mutableStateOf(false) }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.All,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            files.firstOrNull()?.let { file ->
                // Do something with the selected file
                // You can get the ByteArray of the file
                file.readByteArray()
            }
        }
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            /* do something */
                        }) {
                            Icon(
                                Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Localized description",
                            )
                        }
                        Text(
                            "Order",
                            modifier = Modifier
                        )
                        IconButton(onClick = {
                            /* do something */
                        }) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                    }
                }
            )

        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Spacer(modifier = Modifier.padding(innerPadding))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilledTonalButtonExample(
                    onClick = {},
                    label = " Deliver "
                )
                //open local storage and load the image
                FilledTonalButtonExample(
                    onClick = {

                        //  pickerLauncher.launch()
                    },
                    label = " Pick Up "
                )
            }
        }
    }
}