package platform

expect open class PlatformSpecific{
    //fun loadData(): MutableStateFlow<String?>
    fun loadFiles(callback: (String?) -> Unit)
    fun loadImages(callback: (String?) -> Unit)

}