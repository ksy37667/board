import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object DependencyVersions {
  const val SWAGGER_VERSION = "2.9.2"
  const val kotlin_version = "1.3.72"
}

plugins {
    val kotlinVersion = "1.4.32"

    id("com.diffplug.gradle.spotless") version "3.28.1"
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    idea
}

group = "com.board"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

springBoot.buildInfo { properties { } }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation("io.springfox:springfox-swagger-ui:${DependencyVersions.SWAGGER_VERSION}")
    implementation("io.springfox:springfox-swagger2:${DependencyVersions.SWAGGER_VERSION}")

    implementation("com.google.code.gson:gson")

    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")

//    implementation("com.auth0:java-jwt:4.2.1")

    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.plugin:spring-plugin-core:1.2.0.RELEASE")
    // logging
    implementation("io.github.microutils:kotlin-logging:2.1.21")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.4.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
