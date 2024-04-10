plugins {
    kotlin("jvm")
}

group = "de.jugda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":SKaiNET"))
    implementation(project(":SKaiNET-summary"))
    implementation(project(":SKaiNET-io"))
    implementation(project(":K-transformers"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}