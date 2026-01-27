plugins {
    kotlin("jvm") version "2.0.20"
}

configurations {
    create("aspectConfig")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("io.qase:qase-junit5-reporter:4.1.31")
    "aspectConfig"("org.aspectj:aspectjweaver:1.9.22")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
    doFirst {
        val weaver = configurations["aspectConfig"].find { it.name.contains("aspectjweaver") }
        jvmArgs("-javaagent:$weaver")
    }
}
kotlin {
    jvmToolchain(8)
}
