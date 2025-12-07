plugins {
    id("java")
    id("org.springframework.boot") version "3.2.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.duke"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    compileOnly("org.projectlombok:lombok:1.18.42")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("net.bytebuddy:byte-buddy-agent:1.17.8")
}

tasks.test {
    useJUnitPlatform()

    val agentJar = configurations.testRuntimeClasspath.get()
        .filter { it.name.contains("byte-buddy-agent") }
        .singleOrNull()
        ?: throw GradleException("byte-buddy-agent not found in test classpath")

    jvmArgs("-javaagent:$agentJar")
}