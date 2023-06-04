dependencies {
    implementation(project(":infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mybatis:mybatis-spring:2.0.7")
    testImplementation(project(":infrastructure"))
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mariadb")
}

extra["testcontainersVersion"] = "1.17.1"

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.bootJar {
    enabled = true
}
