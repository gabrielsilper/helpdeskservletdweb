plugins {
    id("java")
}

val tomcatVersion = "10.1.20"

group = "com.github.gabrielsilper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.tomcat.embed:tomcat-embed-core:${tomcatVersion}")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:${tomcatVersion}")

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}