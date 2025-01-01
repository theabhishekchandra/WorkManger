plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.abhishek.workmanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.abhishek.workmanager"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // (Java only)
    implementation(libs.androidx.work.runtime)

    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)

    // optional - RxJava2 support
    implementation(libs.androidx.work.rxjava2)

    // optional - GCMNetworkManager support
    implementation(libs.androidx.work.gcm)

    // optional - Test helpers
    androidTestImplementation(libs.androidx.work.testing)

    // optional - Multiprocess support
    implementation(libs.androidx.work.multiprocess)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}