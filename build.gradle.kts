import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.appengine)
}

group = "com.github.imort.equilibria"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(platform(libs.gcloud.platform))
    implementation(libs.gcloud.datastore)

    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.netty)

    implementation(libs.telegram)

    testImplementation(kotlin("test"))
}

configurations.all {
    resolutionStrategy.capabilitiesResolution.withCapability("com.google.guava:listenablefuture") {
        select("com.google.guava:guava:0")
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = libs.versions.java.get()
}

application {
    mainClass.set("MainKt")
}

configure<AppEngineAppYamlExtension> {
    stage {
        setArtifact("build/libs/${project.name}-${version}-all.jar")
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "GCLOUD_CONFIG"
    }
}