plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    val kotlinVersion: String by rootProject.extra
    
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    api("androidx.annotation:annotation:1.0.1")
    api("javax.inject:javax.inject:1")
    api("com.squareup.okhttp3:okhttp:3.12.1")
    api("io.reactivex.rxjava2:rxjava:2.2.5")
    api("com.squareup.retrofit2:retrofit:2.5.0")
    api("com.google.code.gson:gson:2.8.5")

    testImplementation("junit:junit:4.12")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}