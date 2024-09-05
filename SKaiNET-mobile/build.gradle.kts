plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("maven-publish")
}

group = "sk.net.ai"
version = "0.0.2"

kotlin {
    jvmToolchain(17)

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "SKaiNetKit"
        }
    }

    sourceSets {
        commonMain.dependencies {


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