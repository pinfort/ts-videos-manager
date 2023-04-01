package me.pinfort.tsvideosmanager.infrastructure

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(
    scanBasePackages = [
        "me.pinfort.tsvideosmanager"
    ]
)
@MapperScan(
    basePackages = [
        "me.pinfort.tsvideosmanager.infrastructure.database.mapper"
    ]
)
class InfrastructureApplication
