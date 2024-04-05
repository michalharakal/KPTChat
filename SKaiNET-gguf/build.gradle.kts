plugins {
    kotlin("jvm")
}

group = "org.skainet.io.gguf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":SKaiNET"))
    implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.3.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}