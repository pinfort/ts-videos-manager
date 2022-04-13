dependencies {
    implementation(project(":infrastructure"))
}

tasks.bootJar {
    enabled = true
}

springBoot {
    mainClass.set("me.pinfort.tsvideosmanager.console.ConsoleApplication")
}
