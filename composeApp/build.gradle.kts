plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id("com.arkivanov.parcelize.darwin") version "0.2.2"
    id("kotlin-parcelize")
    id("app.cash.sqldelight") version "2.0.0"
    kotlin("plugin.serialization") version "1.9.20"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export(libs.decompose)

            export(libs.essenty.lifecycle)

            // Optional, only if you need state preservation on Darwin (Apple) targets
            export(libs.essenty.stateKeeper)

            // Optional, only if you need state preservation on Darwin (Apple) targets
            export(libs.parcelizeDarwin.runtime)
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.appcompat.appcompat)

            // Ktor
            implementation(libs.ktor.clientAndroid)

            // SqlDelight
            implementation(libs.sqlDelight.androidDriver)

            // Koin
            implementation(libs.koin.android)

            implementation("com.google.android.gms:play-services-auth:21.0.0")

            implementation("com.google.android.gms:play-services-auth-api-phone:18.0.2")

            implementation("androidx.biometric:biometric:1.2.0-alpha05")
            //Firebase
            implementation ("com.google.firebase:firebase-bom:32.1.1")
            implementation (libs.analytics.firebase)
            implementation (libs.crashlytics.firebase)
            implementation("com.googlecode.libphonenumber:libphonenumber:8.2.0")
            // This dependency is downloaded from the Google’s Maven repository.
            // So, make sure you also include that repository in your project's build.gradle file.
            implementation("com.google.android.play:app-update:2.1.0")

            // For Kotlin users also import the Kotlin extensions library for Play In-App Update:
            implementation("com.google.android.play:app-update-ktx:2.1.0")

            api ("com.github.atwa:filepicker:1.0.7")

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.materialIconsExtended)

            implementation(libs.decompose)
            implementation(libs.decompose.jetbrains)
            api(libs.essenty.lifecycle)
            api(libs.essenty.stateKeeper)

            api(libs.ktor.clientCore)
            api(libs.ktor.serializationKotlinxJson)
            api(libs.ktor.clientContentNegotiation)
            api(libs.ktor.clientLogging)

            // Logback for ktor logging
            implementation(libs.logbackClassic)

            //sqldelight
            api(libs.sqlDelight.coroutinesExtensions)
            api(libs.sqlDelight.primitiveAdapters)

            //Koin
            api(libs.koin.core)
            api(libs.koin.test)

//            // KotlinX Serialization Json
            implementation(libs.jetbrains.kotlinx.kotlinxSerializationJson)
//
//            // Coroutines
            implementation(libs.jetbrains.kotlinx.kotlinxCoroutinesCore)
//
//            // MVIKotlin
            api(libs.mvikotlin)
            api(libs.mviKotlin.mvikotlinMain)
            api(libs.mviKotlin.mvikotlinExtensionsCoroutines)
//            // settings
            implementation(libs.russhwolf.settings.core)
            implementation(libs.russhwolf.settings.serialization)
            //insets
            implementation("com.moriatsushi.insetsx:insetsx:0.1.0-alpha10")
        }

        iosMain.dependencies {
            //ios dependencies
            // Ktor
            implementation(libs.ktor.clientDarwin)

            // SqlDelight
            implementation(libs.sqlDelight.nativeDriver)

            api(libs.decompose)

            api(libs.essenty.lifecycle)
            //Modified
            api(libs.parcelizeDarwin.runtime)

        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

