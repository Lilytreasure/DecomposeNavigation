import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

expect open class PlatformSpecific{
    fun loadFiles(callback: (ImageBitmap?) -> Unit)
    fun loadImages(callback: (ImageBitmap?) -> Unit)
    fun launchDialer(phoneNumber: String)
    @Composable
    fun UploadFiles()

    @Composable
    fun CameraView(
    )

}

