plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.github.shaharzohar.netguard.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.shaharzohar.netguard.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Debug: Use full SDK
    debugImplementation(project(":netguard-core"))
    debugImplementation(project(":netguard-captive-portal"))
    debugImplementation(project(":netguard-traffic"))
    debugImplementation(project(":netguard-wifi"))
    debugImplementation(project(":netguard-okhttp"))

    // Release: Use no-op variants
    releaseImplementation(project(":netguard-core-no-op"))
    releaseImplementation(project(":netguard-captive-portal-no-op"))
    releaseImplementation(project(":netguard-traffic-no-op"))
    releaseImplementation(project(":netguard-wifi-no-op"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.bundles.lifecycle)
    implementation(libs.material)
    implementation(libs.okhttp)
}
