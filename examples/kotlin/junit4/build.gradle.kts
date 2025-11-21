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

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "utf-8"
    options.compilerArgs.add("-parameters")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("junit:junit:4.13.1")
    testImplementation("io.qase:qase-junit4-reporter:4.1.24")
    "aspectConfig"("org.aspectj:aspectjweaver:1.9.22")
}

tasks.test {
    useJUnit()
    doFirst {
        val weaver = configurations["aspectConfig"].find { it.name.contains("aspectjweaver") }
        jvmArgs("-javaagent:$weaver")
    }
}
kotlin {
    jvmToolchain(8)
}

