package platform

expect class PermissionHandler {
    suspend fun requestReadStoragePermission(callback: (Boolean) -> Unit)
}