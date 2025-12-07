plugins {
    id("org.springframework.boot") version "3.2.7" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

subprojects {

    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    extra["springCloudVersion"] = "2023.0.3"

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }
}