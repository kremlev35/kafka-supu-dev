import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    val kotlinVersion = "1.5.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "me.nkremlev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring dependencies
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-webflux")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")
    implementation(group = "org.springframework.kafka", name = "spring-kafka")

    // Open-api dependencies
    api(group = "org.springdoc", name = "springdoc-openapi-webflux-core", version = "1.4.0")
    api(group = "org.springdoc", name = "springdoc-openapi-kotlin", version = "1.4.0")
    api(group = "org.springdoc", name = "springdoc-openapi-webflux-ui", version = "1.4.0")

    // Kotlin dependencies
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8")
    implementation(group = "io.projectreactor.kotlin", name = "reactor-kotlin-extensions", version = "1.0.2.RELEASE")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactor")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-jdk8")
    implementation(group = "org.reflections", name = "reflections", version = "0.9.12")

    // Test dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}