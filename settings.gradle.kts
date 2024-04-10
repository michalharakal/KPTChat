plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"

}

rootProject.name = "KPTChat"
include("docs")
include("K-transformers")
include("SKaiNET")
include("SKaiNET-io")
include("SKaiNET-samples")
include("SKaiNET-summary")
include("Javaland-KGPT")
