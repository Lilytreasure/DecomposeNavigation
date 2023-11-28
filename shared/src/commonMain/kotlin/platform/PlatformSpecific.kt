package platform

expect open class PlatformSpecific{
    //fun loadData(): MutableStateFlow<String?>
    fun loadData(callback: (String?) -> Unit)

}