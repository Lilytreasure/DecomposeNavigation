package platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker


@Composable
fun OpenFileWithLibrary() {
    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("jpg", "png")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
        showFilePicker = true
        // do something with the file
    }
}