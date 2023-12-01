plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.9.10"
    id("kotlin-parcelize")
    id("com.arkivanov.parcelize.darwin")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }
    //add  jvm support

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)
                // MVIKotlin
                api(deps.mvikotlin)
                api(deps.mviKotlin.mvikotlinMain)
                api(deps.mviKotlin.mvikotlinExtensionsCoroutines)
                //Decompose Navigation
                implementation(deps.decompose)
                implementation(deps.decompose.jetbrains)
                api(deps.essenty.lifecycle)
                api(deps.essenty.stateKeeper)

                // KotlinX Serialization Json
                implementation(deps.jetbrains.kotlinx.kotlinxSerializationJson)
                // Coroutines
                implementation(deps.jetbrains.kotlinx.kotlinxCoroutinesCore)
                api("com.mohamedrejeb.calf:calf-ui:0.2.0")
                implementation("com.mohamedrejeb.calf:calf-file-picker:0.2.0")
                implementation("com.darkrockstudios:mpfilepicker:3.0.0")
                //permissions
                api("dev.icerock.moko:media:0.11.0")

                // Compose Multiplatform
                api("dev.icerock.moko:media-compose:0.11.0")

                implementation("dev.icerock.moko:media-test:0.11.0")
                //file permisssions access
                api("dev.icerock.moko:permissions:0.16.0")
                // compose multiplatform
                api("dev.icerock.moko:permissions-compose:0.16.0") // permissions api + compose extensions
                implementation("dev.icerock.moko:permissions-test:0.16.0")
                // Biometric
                api("dev.icerock.moko:biometry:0.4.0")

                // Compose Multiplatform
                api("dev.icerock.moko:biometry-compose:0.4.0")

                // Jetpack Compose (only for android, if you don't use multiplatform)
                implementation("dev.icerock.moko:biometry-compose:0.4.0")

                // Koin
                with(deps.koin) {
                    api(core)
                    api(test)
                }
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.8.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                api ("com.github.atwa:filepicker:1.0.9")
                implementation(deps.accompanist.systemUIController)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/commonMain/resources", "src/androidMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
