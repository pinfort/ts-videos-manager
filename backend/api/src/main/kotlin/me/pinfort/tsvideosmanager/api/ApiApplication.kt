package me.pinfort.tsvideosmanager.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "me.pinfort.tsvideosmanager.api",
        "me.pinfort.tsvideosmanager.infrastructure",
    ],
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
