plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.wifi"
    compileSdk = 34
    defaultConfig { minSdk = 24 }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    api(project(":netguard-core-no-op"))
    implementation(libs.kotlin.stdlib)
}

mavenPublishing {
    signAllPublications()
    coordinates("io.github.shaharzohar", "netguard-wifi-no-op", project.version.toString())
    pom {
        name.set("NetGuard WiFi No-Op")
        description.set("No-op WiFi module for release builds")
        url.set("https://github.com/ShaharZohar/netguard-sdk")
        licenses { license { name.set("Apache License 2.0"); url.set("https://www.apache.org/licenses/LICENSE-2.0.txt") } }
        developers { developer { id.set("ShaharZohar"); name.set("Shahar Zohar") } }
        scm { url.set("https://github.com/ShaharZohar/netguard-sdk") }
    }
}
