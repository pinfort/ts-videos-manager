extra["testcontainersVersion"] = "1.19.8"

dependencyManagement {
    imports {
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

dependencies {
    apply(plugin = "java-library")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
    implementation("eu.agno3.jcifs:jcifs-ng:2.1.8")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.2.2")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mariadb")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}

tasks.jar {
    enabled = true
}
