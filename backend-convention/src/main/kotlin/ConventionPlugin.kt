package com.crowdproj.rs.temp.build.plugin

//import com.vanniktech.maven.publish.MavenPublishBaseExtension
//import com.vanniktech.maven.publish.MavenPublishPlugin
//import com.vanniktech.maven.publish.SonatypeHost
import kotlinx.validation.BinaryCompatibilityValidatorPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class ConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        // apply dokka plugin
//        pluginManager.apply(DokkaPlugin::class.java)

        if (rootProject == this) {
            configureRootProject()
        } else {
            configureSubproject()
        }
    }
}

private fun Project.configureRootProject() {
    // configure compatibility validator
    pluginManager.apply(BinaryCompatibilityValidatorPlugin::class.java)
}

private fun Project.configureSubproject() {
    // configure KMP library project
    pluginManager.apply("org.jetbrains.kotlin.multiplatform")
    group = rootProject.group
    version = rootProject.version

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
//            explicitApi()
            configureTargets(this@configureSubproject)
            sourceSets.configureEach {
                languageSettings.apply {
                    languageVersion = "1.9"
                    progressiveMode = true
//                    enableLanguageFeature("NewInference")
                    optIn("kotlin.time.ExperimentalTime")
                }
            }
        }
    }

    // configure detekt
//    pluginManager.apply(DetektPlugin::class.java)
//    dependencies.add("detektPlugins", the<LibrariesForLibs>().plugin.detektFormatting)

    // configure test
//    tasks.withType<Test>().configureEach {
//        useJUnitPlatform()
//        testLogging {
//            events("passed", "skipped", "failed")
//        }
//    }

    // configure publishing
//    pluginManager.apply(MavenPublishPlugin::class.java)
//    extensions.configure<MavenPublishBaseExtension> {
//        publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
////        signAllPublications()
//    }
}

@Suppress("LongMethod", "MagicNumber")
private fun KotlinMultiplatformExtension.configureTargets(project: Project) {
    val libs = project.the<LibrariesForLibs>()
    targets {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(libs.versions.jvm.language.get()))
//            vendor.set(JvmVendorSpec.AZUL)
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        targetHierarchy.default {
            group("native") {
                withLinuxX64()
                withLinuxArm64()
            }
        }

        jvm {
            compilations.configureEach {
                compilerOptions.configure {
                    jvmTarget.set(JvmTarget.valueOf("JVM_${libs.versions.jvm.compiler.get()}"))
                }
            }
        }
        linuxX64()
        linuxArm64()
    }
    project.tasks.withType(JavaCompile::class.java) {
        sourceCompatibility = libs.versions.jvm.language.get()
        targetCompatibility = libs.versions.jvm.compiler.get()
    }
}
