plugins {
    id("java")
}

group = "org.duke"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.1.4.Final")
    implementation("org.slf4j:slf4j-api:2.0.17")

    compileOnly("org.projectlombok:lombok:1.18.42")

    runtimeOnly("org.postgresql:postgresql:42.7.8")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.20")

    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.test {
    useJUnitPlatform()
}