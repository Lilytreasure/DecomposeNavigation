import androidx.compose.ui.graphics.ImageBitmap

expect open class PlatformSpecific{
    fun loadFiles(callback: (ImageBitmap?) -> Unit)
    fun loadImages(callback: (ImageBitmap?) -> Unit)
    fun launchDialer(phoneNumber: String)

}