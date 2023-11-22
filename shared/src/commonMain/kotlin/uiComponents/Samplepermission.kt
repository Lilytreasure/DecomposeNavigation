package uiComponents

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import dev.icerock.moko.media.compose.BindMediaPickerEffect
import dev.icerock.moko.media.compose.rememberMediaPickerControllerFactory
import dev.icerock.moko.media.compose.toImageBitmap
import dev.icerock.moko.media.picker.MediaSource
import kotlinx.coroutines.launch

@Composable
fun Sample() {
    val factory = rememberMediaPickerControllerFactory()
    val picker = remember(factory) { factory.createMediaPickerController() }
    val coroutineScope = rememberCoroutineScope()

    BindMediaPickerEffect(picker)

    var image: ImageBitmap? by remember { mutableStateOf(null) }

    image?.let {
        Image(bitmap = it, contentDescription = null)
    }

    Button(
        onClick = {
            coroutineScope.launch {
                val result = picker.pickImage(MediaSource.GALLERY)
                image = result.toImageBitmap()
            }
        }
    ) {
        Text(text = "Click on me")
    }
}