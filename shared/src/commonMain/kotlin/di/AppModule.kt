package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import platform.PlatformSpecific


fun initKoin(enableNetworkLogs: Boolean = false, platform: PlatformSpecific?, appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        networkModule(enableNetworkLogs,platform),
        module {
            factory { platform }
            single { platform }
        }
    )
}