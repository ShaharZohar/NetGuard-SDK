plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.wifi"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    publishing {
        singleVariant("release") { withSourcesJar(); withJavadocJar() }
    }
}

dependencies {
    api(project(":netguard-core"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.annotation)
    testImplementation(libs.bundles.testing)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates("io.github.shaharzohar", "netguard-wifi", project.version.toString())
    pom {
        name.set("NetGuard WiFi")
        description.set("WiFi monitoring module for NetGuard Developer SDK")
        url.set("https://github.com/ShaharZohar/netguard-sdk")
        licenses { license { name.set("Apache License 2.0"); url.set("https://www.apache.org/licenses/LICENSE-2.0.txt") } }
        developers { developer { id.set("ShaharZohar"); name.set("Shahar Zohar") } }
        scm { url.set("https://github.com/ShaharZohar/netguard-sdk") }
    }
}
