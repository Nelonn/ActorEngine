plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        name = "PaperMC"
        url = uri("https://papermc.io/repo/repository/maven-public/")
        content {
            includeGroupByRegex("io\\.papermc\\..*")
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation("gradle.plugin.org.cadixdev.gradle:licenser:0.6.1")
    implementation("com.github.johnrengelman:shadow:8.1.1")
    implementation("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:1.7.0")
    constraints {
        val asmVersion = 9.7
        val jdependency = 2.10
        implementation("org.ow2.asm:asm:$asmVersion") {
            because("Need Java 21 support in shadow")
        }
        implementation("org.ow2.asm:asm-commons:$asmVersion") {
            because("Need Java 21 support in shadow")
        }
        implementation("org.vafer:jdependency:[$jdependency,)") {
            because("Need Java 21 support in shadow")
        }
    }
}
