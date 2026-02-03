plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.shaharzohar.netguard.core"
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
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview"
        )
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.coroutines)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.startup)

    testImplementation(libs.bundles.testing)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(
        groupId = "io.github.shaharzohar",
        artifactId = "netguard-core",
        version = project.version.toString()
    )

    pom {
        name.set("NetGuard Core")
        description.set("Core module for NetGuard Developer SDK - Network diagnostics for Android")
        inceptionYear.set("2025")
        url.set("https://github.com/ShaharZohar/netguard-sdk")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("ShaharZohar")
                name.set("Shahar Zohar")
                email.set("codeninjaa@gmail.com")
                url.set("https://github.com/ShaharZohar")
            }
        }

        scm {
            url.set("https://github.com/ShaharZohar/netguard-sdk")
            connection.set("scm:git:git://github.com/ShaharZohar/netguard-sdk.git")
            developerConnection.set("scm:git:ssh://git@github.com/ShaharZohar/netguard-sdk.git")
        }
    }
}
