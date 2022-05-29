package me.pinfort.tsvideosmanager.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "me.pinfort.tsvideosmanager",
    ],
)
@EntityScan(
    basePackages = [
        "me.pinfort.tsvideosmanager",
    ]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
