plugins {
    id("com.android.application")
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

        applicationId = "com.moidoc.example.android.flickrverysimpleclientexample"

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("clearPackageData", "true")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":data"))
    //
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0")
    //
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.0-alpha03")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.0-alpha03")
    //
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    //
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
