plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("maven-publish")
}

group = "sk.net.ai"

kotlin {
    jvmToolchain(17)

    jvm()

    sourceSets {
        commonMain.dependencies {

            implementation(project(":SKaiNET"))
            implementation(project(":SKaiNET-summary"))


            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.slf4j)
        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.kotlinx.coroutines.test)
        }

        jvmMain.dependencies { implementation(libs.slf4j) }


        jvmTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.logback)
            implementation(libs.junit)
        }
    }
}