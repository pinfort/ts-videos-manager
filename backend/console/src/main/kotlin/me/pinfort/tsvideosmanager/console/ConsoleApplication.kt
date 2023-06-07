package me.pinfort.tsvideosmanager.console

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
class ConsoleApplication

fun main(args: Array<String>) {
    runApplication<ConsoleApplication>(*args)
}
