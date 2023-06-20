import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"
}

group = "com.simple.atomikos"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // https://mvnrepository.com/artifact/com.atomikos/transactions-spring-boot-starter
    // https://www.atomikos.com/Blog/ExtremeTransactions6dot0#Feature199869_Spring_Boot_3_Starter
    // https://repo.maven.apache.org/maven2/com/atomikos/transactions-spring-boot3-starter/
    implementation("com.atomikos:transactions-spring-boot3-starter:6.0.0M2")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1") // 위에 다 포함된거 아닌가? 왜 영향이 있지?
    implementation("jakarta.jms:jakarta.jms-api:3.1.0") // 위에 다 포함된거 아닌가? 왜 영향이 있지?

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
