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
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

@Composable
fun Sample() {
    val factory = rememberMediaPickerControllerFactory()
    //val picker = remember(factory) { factory.createMediaPickerController() }
    val coroutineScope = rememberCoroutineScope()
    val factoryPermission: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factoryPermission) { factoryPermission.createPermissionsController() }
    val coroutineScopePermission: CoroutineScope = rememberCoroutineScope()
    var image: ImageBitmap? by remember { mutableStateOf(null) }
    var image2: ImageBitmap? by remember { mutableStateOf(null) }

    val coroutineScope2 = CoroutineScope(Dispatchers.IO)
    //new
    val mediaFactory = rememberMediaPickerControllerFactory()
    val picker = remember(mediaFactory) {
        mediaFactory.createMediaPickerController()
    }
    BindMediaPickerEffect(picker)
    image?.let {
        Image(bitmap = it, contentDescription = null)
    }

    Button(
        onClick = {
//            coroutineScope.launch {
//                try {
//                    picker.permissionsController.providePermission(Permission.CAMERA)
//                    if (picker.permissionsController.isPermissionGranted(Permission.CAMERA)) {
//                        image = picker.pickImage(MediaSource.CAMERA).toImageBitmap()
//                    }
//                } catch (ex: CancellationException) {
//                    // Handle cancellation exception here
//                    // This block will be executed if the coroutine is cancelled
//                    println("Coroutine is canceled: ${ex.message}")
//                }
//            }

            coroutineScope.launch {
                try {
                    picker.permissionsController.providePermission(Permission.CAMERA)
                    if (picker.permissionsController.isPermissionGranted(Permission.CAMERA)) {
                        image = picker.pickImage(MediaSource.CAMERA).toImageBitmap()
                        image2 = picker.pickImage(MediaSource.CAMERA).toImageBitmap()

                        // Process the image or perform other tasks
                        println("Captured Camera Image::::::" +image2)
                    }
                } catch (ex: dev.icerock.moko.media.picker.CanceledException) {
                    // Handle media picking cancellation here
                    println("Media picking operation canceled: ${ex.message}")
                } catch (ex: Exception) {
                    // Handle other exceptions if needed
                    println("An unexpected error occurred: ${ex.message}")
                }
            }

//            coroutineScope.launch {
//                picker.permissionsController.providePermission(Permission.CAMERA)
//                if (picker.permissionsController.isPermissionGranted(Permission.CAMERA)) {
//                    image = picker.pickImage(MediaSource.CAMERA).toImageBitmap()
//                    //coroutineScope.cancel()
//                }
//
//            }
//            coroutineScope.launch {
//                    val result = picker.pickImage(MediaSource.GALLERY)
//                    image = result.toImageBitmap()
//
//            }
        }
    ) {
        Text(text = "Click on me")
    }
}