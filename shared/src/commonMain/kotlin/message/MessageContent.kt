package message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.mohamedrejeb.calf.io.name
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import dev.icerock.moko.media.compose.rememberMediaPickerControllerFactory
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import feeds.FilledTonalButtonExample
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.ExperimentalResourceApi
import uiComponents.Sample

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MessageContent(
    component: MessageComponent,
    modifier: Modifier = Modifier,
) {

    val mediaFactory = rememberMediaPickerControllerFactory()
    val picker = remember(mediaFactory) {
        mediaFactory.createMediaPickerController()
    }

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    val coroutineScopePermission: CoroutineScope = rememberCoroutineScope()
    val coroutineScope = rememberCoroutineScope()
    val imagePickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            println("File loading executed:::::::::;")
            if (files.isNotEmpty()) {
                files.firstOrNull()?.let { file ->
                    // Do something with the selected file
                    // You can get the ByteArray of the file
                    if (file.name?.isNotEmpty() == true) {
                        file.name
                    }
                    println("Loaded Data: ${file.name}")
                }
            } else {
                // Handle the case when no file is selected or an issue occurred
                println("Error: File not loaded or issue with file loading.")
            }
        })

    val pdfPickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.All,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            println("File loading executed:::::::::;")
            if (files.isNotEmpty()) {
                files.firstOrNull()?.let { file ->
                    // Do something with the selected file
                    // You can get the ByteArray of the file
                    try {
                        if (file.name?.isNotEmpty() == true) {
                            // Extracted file name
                            val fileName = file.name
                            println("Loaded Data: $fileName")
                        } else {
                            println("Error: File name is empty.")
                        }
                    } catch (e: NumberFormatException) {
                        // Handle the NumberFormatException appropriately
                        println("Error: Failed to parse file name as a number.")
                        e.printStackTrace()
                    }
                }
            } else {
                // Handle the case when no file is selected or an issue occurred
                println("Error: File not loaded or issue with file loading.")
            }
        }
    )

    var showFilePicker by remember { mutableStateOf(false) }
    val fileType = listOf("jpg", "png", "pdf", "*/*")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
        println("Files loaded;;;;;;;;;;;;;::::" + file.toString())

        showFilePicker = false
        // do something with the file


    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            /* do something */
                        }) {
                            Icon(
                                Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Localized description",
                            )
                        }
                        Text(
                            "Order",
                            modifier = Modifier
                        )
                        IconButton(onClick = {
                            /* do something */
                        }) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Spacer(modifier = Modifier.padding(innerPadding))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilledTonalButtonExample(
                    onClick = {
                        //load the pdf
                        //pdfPickerLauncher.launch()
                        //imagePickerLauncher.launch()
//                        coroutineScope.launch {
//                            imagePickerLauncher.launch()
//                            this.cancel()
//                        }
                        //collect a stream of data
//                        component.loadFiles.loadFiles{ loaded->
//                            println("Loaded Passed Value" + loaded)
//                        }
                        component.loadFiles.loadImages {image->
                            println("Loaded camera image + " + image)
                        }

                    },
                    label = " Deliver "
                )
                //open local storage and load the image
                FilledTonalButtonExample(
                    onClick = {

//                        coroutineScope.launch {
//                            controller.providePermission(Permission.STORAGE)
//                            //controller.isPermissionGranted(Permission.STORAGE)
//                            println("The state of Permission +::::::::::;;"+   controller.isPermissionGranted(Permission.STORAGE).toString())
//                        }
                        pdfPickerLauncher.launch()
                        //  showFilePicker = true
                        // imagePickerLauncher.launch()
//                        coroutineScope.launch {
//                            // Request the permission
//                            permissionHandler?.requestReadStoragePermission { isGranted ->
//                                // Handle the result
//                                if (isGranted) {
//                                    println("Permission has been Granted:::::::")
//                                } else {
//                                    println("Permission has been Denied:::::::")
//                                }
//                            }
//                        }

                    },
                    label = " Pick Up "
                )
            }

            //image preview
            Sample()
            // SampleAccess()

        }
    }
}


