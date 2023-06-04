dependencies {
    implementation(project(":infrastructure"))
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("org.mybatis:mybatis-spring:2.0.7")
    testImplementation(project(":infrastructure"))
}

tasks.bootJar {
    enabled = true
}
