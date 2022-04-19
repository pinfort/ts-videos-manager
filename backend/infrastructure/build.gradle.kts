extra["testcontainersVersion"] = "1.17.1"

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

dependencies {
    apply(plugin = "java-library")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.2")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mariadb")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}
