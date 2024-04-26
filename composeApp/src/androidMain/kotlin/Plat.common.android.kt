import java.util.UUID

actual class PlatformStorableImage


actual fun createUUID(): String = UUID.randomUUID().toString()