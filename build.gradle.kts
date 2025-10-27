plugins {
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.kotlin.spring) apply true
    alias(libs.plugins.spring.boot) apply true
    alias(libs.plugins.spring.dependency.management) apply true
}

group = "org.github.swszz"
version = "0.1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stblib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.coroutines.test)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.test)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.kotlin.junit.test)


    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}