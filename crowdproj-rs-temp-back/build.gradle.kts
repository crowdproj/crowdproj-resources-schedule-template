plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    id("backend-convention")
}

group = "com.crowdproj.rs.temp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//val ktJvmPluginId: String = libs.plugins.kotlin.jvm.get().pluginId
//val ktKmpPluginId: String = libs.plugins.kotlin.multiplatform.get().pluginId
//val jvmTarget: String = libs.versions.jvm.compiler.get()

subprojects {
    this.group = rootProject.group
    this.version = rootProject.version

    repositories {
        mavenLocal()
        mavenCentral()
    }
//    tasks.withType<KotlinJvmCompile> {
//        kotlinOptions.jvmTarget = jvmTarget
//    }
//    tasks.withType<KotlinCompile> {
//        kotlinOptions.jvmTarget = jvmTarget
//    }
}

afterEvaluate {
    val deploy: Task by tasks.creating {
        println("VER: ${project.version}")
        group = "build"
        dependsOn("build")
//        dependsOn(project(":crowdproj-ad-app-ktor").getTasksByName("deploy", false))
    }
}