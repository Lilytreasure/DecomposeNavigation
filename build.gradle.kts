buildscript {
    dependencies {
        classpath(deps.jetbrains.kotlinx.binaryCompatibilityValidator)
        classpath(deps.parcelizeDarwin.gradlePlug)
        classpath(deps.kotlin.kotlinGradlePlug)
        classpath("com.android.tools.build:gradle:8.1.1")
    }
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)

}
