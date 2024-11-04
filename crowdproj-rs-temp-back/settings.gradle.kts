rootProject.name = "crowdproj-rs-temp-back"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../backend-convention")
    plugins {
        id("backend-convention") version "1.0.0" apply false
    }
}

//include("crowdproj-rs-temp-common")