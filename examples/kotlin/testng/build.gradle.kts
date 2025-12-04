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
    testImplementation("org.testng:testng:7.5")
    testImplementation("io.qase:qase-testng-reporter:4.1.25")
    "aspectConfig"("org.aspectj:aspectjweaver:1.9.22")
}

tasks.test {
    useTestNG()
    doFirst {
        val weaver = configurations["aspectConfig"].find { it.name.contains("aspectjweaver") }
        jvmArgs("-javaagent:$weaver")
    }
}
kotlin {
    jvmToolchain(8)
}
