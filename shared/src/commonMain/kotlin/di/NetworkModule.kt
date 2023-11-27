package di
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.LoadFiles

val networkModule: (enableLogging: Boolean, platform: LoadFiles?) -> Module get() = { enableLogging,  platform ->
    module {
        single { enableLogging  }
        single { platform }
    }
}