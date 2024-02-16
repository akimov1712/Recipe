import ru.topbun.buildsrc.MetaData
import ru.topbun.buildsrc.Dependency

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("com.google.devtools.ksp")
    id ("kotlin-parcelize")
    id ( "androidx.navigation.safeargs.kotlin")
    id ( "com.google.dagger.hilt.android")
}

android {
    namespace = (MetaData.applicationId)
    compileSdk = (MetaData.compileSdkVersion)

    defaultConfig {
        applicationId = (MetaData.applicationId)
        minSdk = (MetaData.minSdkVersion)
        targetSdk = (MetaData.targetSdkVersion)
        versionCode = (MetaData.versionCode)
        versionName = (MetaData.versionName)

        testInstrumentationRunner = (MetaData.testInstrumentationRunner)

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

//    Paging3
    implementation (Dependency.pagingRuntime)
    implementation (Dependency.pagingCommonKxt)
    implementation (Dependency.pagingCommonAndroid)

//    Hilt
    implementation (Dependency.daggerAndroid)
    ksp (Dependency.daggerCompiler)

//    View
    implementation (Dependency.picasso)
    implementation (Dependency.roundedImage)
    implementation (Dependency.swipeRefresh)

//    Navigation
    implementation (Dependency.navigationFragment)
    implementation (Dependency.navigationUi)

//    JSON
    implementation (Dependency.serializationKotlin)
    implementation (Dependency.gson)
    implementation (Dependency.jsoup)

//    Room
    implementation (Dependency.roomRuntime)
    ksp (Dependency.roomCompiler)
    implementation (Dependency.roomKtx)

//    ViewModel
    implementation (Dependency.viewModel)

//    Default
    implementation (Dependency.coreKtx)
    implementation (Dependency.appcompat)
    implementation (Dependency.material)
    implementation (Dependency.constraintlayout)
    testImplementation (Dependency.junit)
    androidTestImplementation (Dependency.junitExt)
    androidTestImplementation (Dependency.espresso)

}