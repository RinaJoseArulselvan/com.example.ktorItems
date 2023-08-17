val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
                id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // database connection with postgresql
    implementation("org.postgresql:postgresql:42.6.0")

    // Hikari Datasource
    implementation ("com.zaxxer:HikariCP:3.4.2")

    //encryption and decryption
    implementation("org.mindrot:jbcrypt:0.4")

    //exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    // request validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    //status pages
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    //authentication
    implementation("io.ktor:ktor-server-auth:$ktor_version")

    //jwt authentication
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")

    //Http client and serialization using ktor client
    implementation("io.ktor:ktor-client-apache:1.6.2")
    implementation("io.ktor:ktor-client-serialization-jvm:1.6.2")

    //to convert into json values
    implementation("com.google.code.gson:gson:2.8.9")

    //implementing redis in-memory storage
    implementation("redis.clients:jedis:3.7.0")

    //sessions
    implementation("io.ktor:ktor-server-sessions:$ktor_version")

    //koin
    implementation("io.insert-koin:koin-ktor:3.2.0")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}