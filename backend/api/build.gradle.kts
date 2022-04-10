plugins {
    kotlin("jvm") version "1.6.20"
}



repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
}