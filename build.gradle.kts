import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
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
}

dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.yarn.mappings) { classifier("v2") })
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kt)

    implementation("org.lwjgl:lwjgl-glfw:3.3.2")
    modImplementation(include(libs.midnightlib.get().toString())!!)
    modImplementation(libs.mod.menu)
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    val targetJavaVersion = 17
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = targetJavaVersion.toString()
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