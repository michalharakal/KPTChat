plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"

}

rootProject.name = "KPTChat"
include("docs")
include("KnanoGPT")
include("SKaiNET")
include("SKaiNET-samples")
include("SKaiNET-reflection")
include("SKaiNET-processor")
