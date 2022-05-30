dependencies {
    implementation(project(":infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation(project(":infrastructure"))
}

tasks.bootJar {
    enabled = true
}
