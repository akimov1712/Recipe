package ru.topbun.buildsrc

object PluginVersion {
    const val APPLICATION = "8.1.4"
    const val KOTLIN_ANDROID = "1.8.0"
    const val KOTLIN_JVM = "1.9.0"
    const val KSP = "1.9.0-1.0.12"
    const val SAFE_ARGS = "2.7.5"
    const val HILT = "2.48.1"
}

object Plugin {
    const val APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_KSP = "com.google.devtools.ksp"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val NAVIGATION_SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
    const val HILT_ANDROID = "com.google.dagger.hilt.android"
}

object PluginDependency {
    const val APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_JVM = "com.google.devtools.ksp"
    const val KSP = "org.jetbrains.kotlin.jvm"
    const val SAFE_ARGS = "androidx.navigation.safeargs"
    const val HILT = "com.google.dagger.hilt.android"
}
