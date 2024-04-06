plugins {
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    kotlin("jvm")
}

group = "de.jugda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":SKaiNET"))
    implementation(project(":SKaiNET-reflection"))

    implementation(project(":SKaiNET-processor"))
    ksp(project(":SKaiNET-processor"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("junit:junit:4.13.2")
}

ksp {
    arg("option1", "value1")
    arg("option2", "value2")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}