package platform

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

actual class PermissionHandler(private val activity: ComponentActivity) {
    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Callback to the shared code
            callback?.let { it(isGranted) }
        }

    actual suspend fun requestReadStoragePermission(callback: (Boolean) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            callback(true)
        } else {
            // Assign the callback to a property so it can be used later
            this.callback = callback
            // Request permission using the launcher
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    // Property to store the callback
    private var callback: ((Boolean) -> Unit)? = null

    // Handle the result of the permission request
    fun onRequestPermissionsResult(isGranted: Boolean) {
        // Ensure the callback is not null
        callback?.invoke(isGranted)
        // Reset the callback to null
        callback = null
    }


}