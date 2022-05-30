package me.pinfort.tsvideosmanager.api

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "me.pinfort.tsvideosmanager",
    ],
)
@MapperScan(
    basePackages = [
        "me.pinfort.tsvideosmanager.infrastructure.database.mapper",
    ]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
