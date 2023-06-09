package me.pinfort.tsvideosmanager.api

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "me.pinfort.tsvideosmanager"
    ]
)
@ConfigurationPropertiesScan(
    basePackages = [
        "me.pinfort.tsvideosmanager.infrastructure.config"
    ]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
