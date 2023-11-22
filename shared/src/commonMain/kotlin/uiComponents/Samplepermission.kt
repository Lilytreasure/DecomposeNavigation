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

            coroutineScope.launch {
                picker.permissionsController.providePermission(Permission.CAMERA)
                if (picker.permissionsController.isPermissionGranted(Permission.CAMERA)) {
                    image = picker.pickImage(MediaSource.CAMERA).toImageBitmap()
                }
            }

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