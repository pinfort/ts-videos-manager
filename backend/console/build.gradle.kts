dependencies {
    implementation(project(":infrastructure"))
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = true
}
