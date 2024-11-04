plugins {
    `kotlin-dsl`
//    alias(libs.plugins.detekt)
}

//kotlin {
//    jvmToolchain {
//        languageVersion.set(JavaLanguageVersion.of(libs.versions.jvm.language.get()))
////        vendor.set(JvmVendorSpec.AZUL)
//    }
//}

//tasks.withType<KotlinCompile>().configureEach {
//    compilerOptions {
//        jvmTarget.set(JvmTarget.valueOf("JVM_${libs.versions.jvm.compiler}"))
//    }
//}

//tasks.withType<JavaCompile>().configureEach {
//    sourceCompatibility = libs.versions.jvm.language.get()
//    targetCompatibility = libs.versions.jvm.compiler.get()
//}

gradlePlugin {
    plugins {
        register("convention") {
            id = "backend-convention"
            implementationClass = "com.crowdproj.rs.temp.build.plugin.ConventionPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
    implementation(libs.plugin.mavenPublish)
}
