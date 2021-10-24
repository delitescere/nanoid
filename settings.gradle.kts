rootProject.name = "nanoid"

// get kotlinVersion from gradle.properties into build.gradle.kts plugins section without duplicating
pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm").version(kotlinVersion)
    }
}

include("lib")
