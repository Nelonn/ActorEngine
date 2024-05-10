import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
    id("io.papermc.paperweight.userdev")
}

group = rootProject.group
version = rootProject.version

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") // Mixin
}

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

tasks.named<Copy>("processResources") {
    filteringCharset = "UTF-8"
    filesMatching("coprolite.plugin.json") {
        expand("version" to version)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    relocate("me.nelonn.bestvecs", "me.nelonn.entitycomposer.shaded.bestvecs")
    relocate("me.nelonn.pluginlib", "me.nelonn.entitycomposer.shaded.pluginlib")
}

tasks.named("assemble").configure {
    dependsOn("reobfJar")
    dependsOn("shadowJar")
}

tasks.reobfJar {
    remapperArgs.add("--mixin")
}
