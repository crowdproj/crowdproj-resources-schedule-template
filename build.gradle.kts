plugins {
    id("com.crowdproj.plugin.autoversion") version "0.0.5"
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose) apply false
}

group = "com.crowdproj.rs.temp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

autoversion {
    shoudIncrement.set(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
