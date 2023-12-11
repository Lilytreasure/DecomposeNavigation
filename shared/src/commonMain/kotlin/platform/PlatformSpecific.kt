package platform

import androidx.compose.ui.graphics.ImageBitmap

expect open class PlatformSpecific{
    //fun loadData(): MutableStateFlow<String?>
    fun loadFiles(callback: (ImageBitmap?) -> Unit)
    fun loadImages(callback: (ImageBitmap?) -> Unit)

}