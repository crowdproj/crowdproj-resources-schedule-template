import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "com.crowdproj.marketplace"
version = "0.0.1"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    this.group = group
    this.version = version

    tasks.withType<KotlinCompile> {
        val jvmTarget: String by project
        kotlinOptions.jvmTarget = jvmTarget
    }
}

tasks.wrapper {
    gradleVersion = "8.10.2"
    // You can either download the binary-only version of Gradle (BIN) or
    // the full version (with sources and documentation) of Gradle (ALL)
    distributionType = Wrapper.DistributionType.ALL
}