plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}
//
val kotlinVersion: String by rootProject.extra
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
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
