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
//include("K-transformers")
include("SKaiNET")
//include("SKaiNET-mikrograd")
// include("SKaiNET-io")
//include("SKaiNET-samples")
include("SKaiNET-summary")
