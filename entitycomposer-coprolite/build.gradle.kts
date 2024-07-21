plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
    id("io.github.goooler.shadow")
}

group = rootProject.group
version = rootProject.version

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") // Mixin
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle(project.properties["paper_build"].toString())
    compileOnly(fileTree("libs/compile"))
    implementation(fileTree("libs/implement"))
    compileOnly("org.spongepowered:mixin:0.8.5")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.encoding = "UTF-8"
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("coprolite.plugin.json") {
            expand("version" to version)
        }
    }

    shadowJar {
        archiveClassifier.set("")
        relocate("me.nelonn.bestvecs", "me.nelonn.entitycomposer.shaded.bestvecs")
        relocate("me.nelonn.pluginlib", "me.nelonn.entitycomposer.shaded.pluginlib")
    }

    /*reobfJar {
        remapperArgs.add("--mixin")
    }*/

    assemble {
        //dependsOn("reobfJar")
        dependsOn("shadowJar")
    }
}
