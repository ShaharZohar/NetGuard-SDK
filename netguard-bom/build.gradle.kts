plugins {
    `java-platform`
    alias(libs.plugins.maven.publish)
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api(project(":netguard-core"))
        api(project(":netguard-core-no-op"))
        api(project(":netguard-captive-portal"))
        api(project(":netguard-captive-portal-no-op"))
        api(project(":netguard-traffic"))
        api(project(":netguard-traffic-no-op"))
        api(project(":netguard-wifi"))
        api(project(":netguard-wifi-no-op"))
        api(project(":netguard-okhttp"))
    }
}

mavenPublishing {
    signAllPublications()
    
    coordinates(
        groupId = "io.github.shaharzohar",
        artifactId = "netguard-bom",
        version = project.version.toString()
    )

    pom {
        name.set("NetGuard BOM")
        description.set("Bill of Materials for NetGuard Developer SDK - ensures version alignment")
        url.set("https://github.com/ShaharZohar/netguard-sdk")
        packaging = "pom"

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
            connection.set("scm:git:git://github.com/ShaharZohar/netguard-sdk.git")
            developerConnection.set("scm:git:ssh://git@github.com/ShaharZohar/netguard-sdk.git")
        }
    }
}
