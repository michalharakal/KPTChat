plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "KPTChat"
include("docs")
include("KnanoGPT")
include("SKaiNET")
include("SKaiNET-gguf")
include("SKaiNET-samples")
