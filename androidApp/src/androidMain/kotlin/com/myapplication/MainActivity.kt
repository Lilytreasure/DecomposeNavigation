package com.myapplication

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import platform.LoadFiles
import rootBottomStack.DefaultRootBottomComponent

class MainActivity : AppCompatActivity() {
    private var platform: LoadFiles? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platform = LoadFiles(this)
        val root = DefaultRootBottomComponent(
            componentContext = defaultComponentContext(),
        )
        try {
            val koinApplication = initKoin(
                // add to build configuration, false in prod
                enableNetworkLogs = true,
                platform = platform
            )
            println("Koin app  Started::;::::")
            koinApplication.androidContext(applicationContext)
        } catch (e: KoinAppAlreadyStartedException) {
            println("Koin app  Failed::;::::")
            e.printStackTrace()
        }
        setContent {
            MainView(root)
        }
    }

    override fun onDestroy() {
        stopKoin()
        super.onDestroy()
    }
}