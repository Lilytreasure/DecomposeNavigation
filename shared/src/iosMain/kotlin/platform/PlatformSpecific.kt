package platform

import androidx.compose.ui.graphics.ImageBitmap

actual open class  PlatformSpecific{
    actual fun loadFiles(callback: (ImageBitmap?) -> Unit) {
        //convert  an image to a Imagebitmap equivalent in swift
    }

    actual fun loadImages(callback: (ImageBitmap?) -> Unit) {
    }

    actual fun launchDialer(phoneNumber: String) {

        //swift launch dialer implementation
    }


}