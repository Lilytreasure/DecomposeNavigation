import androidx.compose.ui.graphics.ImageBitmap
import platform.AVFoundation.AVMediaTypeText
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHPhotoLibrary
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationAssetRepresentationModeCurrent
import platform.PhotosUI.PHPickerConfigurationSelectionOrdered
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerSourceType


actual open class PlatformSpecific {
    actual fun loadFiles(callback: (ImageBitmap?) -> Unit) {
        //Todo ----
        //convert  an image to a Imagebitmap equivalent in swift
        val configuration = PHPickerConfiguration(PHPhotoLibrary.sharedPhotoLibrary())
        val newFilter =
            PHPickerFilter.anyFilterMatchingSubfilters(
                listOf(
                    PHPickerFilter.imagesFilter(),
                ),
            )
        configuration.filter = newFilter
        configuration.preferredAssetRepresentationMode =
            PHPickerConfigurationAssetRepresentationModeCurrent
        configuration.selection = PHPickerConfigurationSelectionOrdered
        configuration.selectionLimit = 1
        configuration.preselectedAssetIdentifiers = listOf<Nothing>()
        val picker = PHPickerViewController(configuration)
        picker.delegate
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            picker,
            true,
            null,
        )
    }

    actual fun loadImages(callback: (ImageBitmap?) -> Unit) {
        val cameraVc = UIImagePickerController()
        platform.AVFoundation.AVCaptureDevice.requestAccessForMediaType(mediaType = AVMediaTypeVideo) {
            if (it) {
                println("Access granted;;;;")
                //access granted
//                cameraVc.sourceType =
//                    UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
//                UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
//                    cameraVc,
//                    true,
//                    null,
//                )
            } else {

                println("Acces denied;;;;;;;")

            }
        }

    }

    actual fun launchDialer(phoneNumber: String) {
        //Todo-------
//
//		val url = NSURL( "tel://9809088798")
//
//        if (UIApplication.sharedApplication().canOpenURL(url))
//        {
//            UIApplication.sharedApplication().openURL(url)
//        }
//    }
        val urlString = "tel://$phoneNumber"
        val url = NSURL(string = urlString)
//        val url =
//            NSURL(string = "tel://(phoneNumber)")

        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }

    }
}
