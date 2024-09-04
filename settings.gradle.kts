pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KPTChat"
//include("docs")
include("SKaiNET")
include("SKaiNET-summary")
include("SKaiNET-io")
//include("K-transformers")
//include("SKaiNET-samples")

