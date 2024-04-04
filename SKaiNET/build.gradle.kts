plugins {
    kotlin("jvm")
    id("maven-publish")
}

group = "sk.ai.net"
version = "0.0.1"

repositories {
    mavenCentral()

}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

publishing {


    publications {
        // Create a publication named 'myLibrary'
        create("skainet", MavenPublication::class) {
            // Set the artifact ID
            artifactId = "core"

            // Include components from the 'java' plugin
            from(components["java"])
        }
    }

    repositories {
        // Publish to the local Maven repository
        mavenLocal()
    }
}