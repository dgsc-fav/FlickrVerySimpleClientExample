plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}
//
val kotlinVersion: String by rootProject.extra
val daggerVersion: String by rootProject.extra
//
val compileSdkVersionGlobal: Int by rootProject.extra
val minSdkVersionGlobal: Int by rootProject.extra
val targetSdkVersionGlobal: Int by rootProject.extra

android {
    compileSdkVersion(compileSdkVersionGlobal)

    defaultConfig {
        minSdkVersion(minSdkVersionGlobal)
        targetSdkVersion(targetSdkVersionGlobal)

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("clearPackageData", "true")

        buildConfigField("String", "FLICKR_API_KEY", "\"da9d38d3dee82ec8dda8bb0763bf5d9c\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    //
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.annotation:annotation:1.1.0")
    //
    api("com.google.dagger:dagger:$daggerVersion")
    api("com.google.code.findbugs:jsr305:3.0.2") // Required by Dagger (see https://stackoverflow.com/q/19030954/45668)
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    //
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
    //
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    //
    api("com.jakewharton.timber:timber:4.7.1")
    //
    api("androidx.room:room-runtime:2.1.0")
    api("androidx.room:room-rxjava2:2.1.0")
    kapt("androidx.room:room-compiler:2.1.0")
    //
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
