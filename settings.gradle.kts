rootProject.name = "crowdproj-rs-temp"

includeBuild("crowdproj-rs-temp-back")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.google.com/")
    }
}
