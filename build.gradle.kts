
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "FirstTask"
version = "0.0.1"

application {
    mainClass = "FirstTask.ApplicationKt"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repos.spring.io/plugins-release")
    }
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.core)
    implementation("com.h2database:h2:2.2.224")
}
