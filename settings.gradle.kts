pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "KPTChat"
//include("docs")
//include("K-transformers")
include("SKaiNET")
//include("SKaiNET-mikrograd")
// include("SKaiNET-io")
//include("SKaiNET-samples")
//include("SKaiNET-summary")
