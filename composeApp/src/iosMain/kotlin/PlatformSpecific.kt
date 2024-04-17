import androidx.compose.ui.graphics.ImageBitmap
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

        //Todo--lauch camera and take photo
//        val vc = UIImagePickerController()
//        vc.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
//        platform.AVFoundation.AVCaptureDevice.requestAccessForMediaType(mediaType = "\"public.image\"") {
//            if (it) {
//                println("Permitted camera")
//
//            } else {
//                println(" Not Permitted camera")
//            }
//        }
//        // self.present(vc, animated: true, completion: nil)
//        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
//            vc,
//            true,
//            null,
//        )
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
