plugins {
    kotlin("jvm") version "1.6.20"
}

group = "me.pinfort"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
    runtimeOnly("mysql:mysql-connector-java")
}