plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.captiveportal"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    api(project(":netguard-core-no-op"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.annotation)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(
        groupId = "io.github.shaharzohar",
        artifactId = "netguard-captive-portal-no-op",
        version = project.version.toString()
    )

    pom {
        name.set("NetGuard Captive Portal No-Op")
        description.set("No-op version of captive portal module for release builds")
        url.set("https://github.com/ShaharZohar/netguard-sdk")
        
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        
        developers {
            developer {
                id.set("ShaharZohar")
                name.set("Shahar Zohar")
            }
        }
        
        scm {
            url.set("https://github.com/ShaharZohar/netguard-sdk")
        }
    }
}
