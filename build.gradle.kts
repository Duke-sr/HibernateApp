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

    testImplementation("org.junit.jupiter:junit-jupiter:6.0.0")
    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")
    testImplementation("org.junit.platform:junit-platform-launcher:6.0.0")
    testImplementation("net.bytebuddy:byte-buddy-agent:1.17.8")
}

tasks.test {
    useJUnitPlatform()
    val agent = configurations.testRuntimeClasspath.get()
        .filter { it.name.contains("byte-buddy-agent") }
        .singleFile

    jvmArgs("-javaagent:$agent")
}