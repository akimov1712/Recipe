package ru.topbun.buildsrc

object Version {
    const val room = "2.6.1"
    const val hilt = "2.48.1"
    const val navComponent = "2.7.5"
    const val paging3 = "3.2.1"
    const val paging3Android = "3.3.0-alpha02"

    const val picasso = "2.71828"
    const val roundedImage = "2.3.0"
    const val swipeRefresh = "1.2.0-alpha01"
    const val serializationKotlin = "1.5.1"
    const val gson = "2.10.1"
    const val jsoup = "1.16.2"
    const val viewModel = "2.6.2"

    const val coreKtx = "1.12.0"
    const val appcompat = "1.6.1"
    const val material = "1.10.0"
    const val constraintlayout = "2.1.4"
    const val junit = "4.13.2"
    const val junitExt = "1.1.5"
    const val espresso = "3.5.1"

}

object Dependency {

    //    Paging3
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Version.paging3}"
    const val pagingCommonKxt = "androidx.paging:paging-common-ktx:${Version.paging3}"
    const val pagingCommonAndroid = "androidx.paging:paging-common-android:${Version.paging3Android}"

    //    Hilt
    const val daggerAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
    const val daggerCompiler = "com.google.dagger:hilt-compiler:${Version.hilt}"

    //    View
    const val picasso = "com.squareup.picasso:picasso:${Version.picasso}"
    const val roundedImage = "com.makeramen:roundedimageview:${Version.roundedImage}"
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefresh}"

    //    Navigation
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navComponent}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.navComponent}"

    //    JSON
    const val serializationKotlin = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serializationKotlin}"
    const val gson = "com.google.code.gson:gson:${Version.gson}"
    const val jsoup = "org.jsoup:jsoup:${Version.jsoup}"

    //    Room
    const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
    const val roomKtx = "androidx.room:room-ktx:${Version.room}"

    //    ViewModel
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModel}"

    //    Default
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Version.constraintlayout}"
    const val junit = "junit:junit:${Version.junit}"
    const val junitExt = "androidx.test.ext:junit:${Version.junitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
}