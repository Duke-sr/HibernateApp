plugins {
    id("java")
    id("org.springframework.boot") version "3.2.7"
    id("io.spring.dependency-management")
}

group = "com.duke"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("org.glassfish.jersey.core:jersey-server:3.1.7")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.1.7")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}