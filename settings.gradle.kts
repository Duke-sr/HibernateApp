pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "HibernateApp"

include("user-service")
include("notification-service")
include("common")
include("gateway-service")
include("discovery-service")