dependencies {
    implementation(project(":infrastructure"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    testImplementation(project(":infrastructure"))
}

tasks.bootJar {
    enabled = true
}
