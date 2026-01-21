plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

tasks.withType<JavaCompile>().configureEach {
    // sourceCompatibility = JavaVersion.VERSION_1_8
    // targetCompatibility = JavaVersion.VERSION_1_8
    options.encoding = "utf-8"
}

val cucumberVersion = "7.14.0"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.cucumber:cucumber-core:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    testImplementation("io.qase:qase-cucumber-v7-reporter:4.1.30")
}

tasks.test {
    useJUnitPlatform()
    val props = System.getProperties()
    props.forEach { key, value ->
        systemProperty(key.toString(), value.toString())
    }
}
