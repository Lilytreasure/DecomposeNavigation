package di
import org.koin.core.module.Module
import org.koin.dsl.module
import PlatformSpecific

val networkModule: (enableLogging: Boolean, platform: PlatformSpecific?) -> Module get() = { enableLogging, platform ->
    module {
        single { enableLogging  }
        single { platform }
    }
}