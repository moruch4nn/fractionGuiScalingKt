import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("fabric-loom")
}

group = project.property("mavenGroup") as String
version = project.property("modVersion") as String

base {
    archivesName.set(project.property("archivesBaseName") as String)
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraftVersion")}")
    mappings("net.fabricmc:yarn:${project.property("yarnMappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${project.property("loaderVersion")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabricVersion")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabricKotlinVersion")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    val javaVersion = JavaVersion.VERSION_17

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "$javaVersion"
        targetCompatibility = "$javaVersion"
        options.release.set("$javaVersion".toInt())
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "$javaVersion"
        }
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of("$javaVersion"))
        }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}