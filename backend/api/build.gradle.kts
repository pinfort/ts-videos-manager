dependencies {
    implementation(project(":infrastructure"))
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.bootJar {
    enabled = true
}

springBoot {
    mainClass.set("me.pinfort.tsvideosmanager.api.ApiApplication")
}