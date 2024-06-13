import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

group = project.properties["maven_group"]!!
version = project.properties["mod_version"]!!
base.archivesName.set(project.properties["archives_base_name"] as String)
description = "Make GLFW use wayland on supported systems"
val modid = project.properties["modid"]!! as String

repositories {
    mavenCentral()
    maven("https://maven.terraformersmc.com/releases")
    maven("https://api.modrinth.com/maven")
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.yarn.mappings) { classifier("v2") })
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kt)

    modImplementation(libs.fabric.api)
    modImplementation(libs.yacl)
    modImplementation(libs.mod.menu)
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    val targetJavaVersion = 21
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile>().all {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
        withSourcesJar()
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}"}
        }
    }
}