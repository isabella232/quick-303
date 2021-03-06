import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

val kotlinVersion = "1.2.70"

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow").version("2.0.4")
}

dependencies {
    implementation("com.atlassian.performance.tools:virtual-users:[3.10.0,4.0.0)")
    implementation("org.seleniumhq.selenium:selenium-support:3.11.0")
    implementation("org.glassfish:javax.json:1.1")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.getByName("shadowJar", ShadowJar::class).apply {
    manifest.attributes["Main-Class"] = "com.atlassian.performance.tools.virtualusers.api.EntryPointKt"
}

configurations.all {
    resolutionStrategy {
        activateDependencyLocking()
        failOnVersionConflict()
        dependencySubstitution {
            substitute(module("org.apache.logging.log4j:log4j-slf4j-impl"))
                .with(module("org.apache.logging.log4j:log4j-slf4j18-impl:2.12.1"))
        }
        eachDependency {
            when (requested.module.toString()) {
                "commons-codec:commons-codec" -> useVersion("1.10")
                "com.google.code.gson:gson" -> useVersion("2.8.2")
                "org.slf4j:slf4j-api" -> useVersion("1.8.0-alpha2")
            }
            when (requested.group) {
                "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
                "org.apache.logging.log4j" -> useVersion("2.12.1")
            }
        }
    }
}

repositories {
    mavenLocal()
    maven(url = URI("https://packages.atlassian.com/maven-external/"))
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}