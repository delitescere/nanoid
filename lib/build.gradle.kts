plugins {
    kotlin("jvm")
    `java-library`
    idea
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1"
}

repositories {
    mavenCentral()
}

group = "id.nanoid"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(platform(kotlin("bom")))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.assertj:assertj-core:3.21.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("failed")
    }
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        exclude("ch.qos.logback", "logback-classic")
        exclude("org.apache.logging.log4j", "log4j-to-slf4j")
        exclude("org.slf4j", "jul-to-slf4j")
    }
}
