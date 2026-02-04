plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.okhttp"
    compileSdk = 34
    defaultConfig { minSdk = 24; consumerProguardFiles("consumer-rules.pro") }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    api(project(":netguard-core"))
    api(project(":netguard-traffic"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.okhttp)
    implementation(libs.okhttp.dnsoverhttps)
}

mavenPublishing {
    signAllPublications()
    coordinates("io.github.shaharzohar", "netguard-okhttp", project.version.toString())
    pom {
        name.set("NetGuard OkHttp")
        description.set("OkHttp integration for NetGuard Developer SDK")
        url.set("https://github.com/ShaharZohar/netguard-sdk")
        licenses { license { name.set("Apache License 2.0"); url.set("https://www.apache.org/licenses/LICENSE-2.0.txt") } }
        developers { developer { id.set("ShaharZohar"); name.set("Shahar Zohar") } }
        scm { url.set("https://github.com/ShaharZohar/netguard-sdk") }
    }
}
