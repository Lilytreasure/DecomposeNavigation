package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import platform.LoadFiles


fun initKoin(enableNetworkLogs: Boolean = false, platform: LoadFiles?,appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        networkModule(enableNetworkLogs,platform),
        module {
            factory { platform }
            single { platform }
        }
    )
}