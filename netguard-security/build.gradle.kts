plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.security"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    api(project(":netguard-core"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.okhttp)

    testImplementation(libs.bundles.testing)
}

mavenPublishing {
    signAllPublications()

    coordinates(
        groupId = "io.github.shaharzohar",
        artifactId = "netguard-security",
        version = project.version.toString()
    )

    pom {
        name.set("NetGuard Security")
        description.set("Network security analysis module for NetGuard Developer SDK")
        inceptionYear.set("2025")
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
                email.set("codeninjaa@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/ShaharZohar/netguard-sdk")
        }
    }
}
