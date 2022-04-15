dependencies {
    apply(plugin = "java-library")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
    runtimeOnly("mysql:mysql-connector-java")
}
