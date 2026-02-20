pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "netguard-sdk"

// Core modules
include(":netguard-core")
include(":netguard-core-no-op")

// Feature modules
include(":netguard-captive-portal")
include(":netguard-captive-portal-no-op")
include(":netguard-traffic")
include(":netguard-traffic-no-op")
include(":netguard-wifi")
include(":netguard-wifi-no-op")

// Integration modules
include(":netguard-okhttp")

// Security module
include(":netguard-security")

// BOM for version alignment
include(":netguard-bom")

// Sample application
include(":sample")
