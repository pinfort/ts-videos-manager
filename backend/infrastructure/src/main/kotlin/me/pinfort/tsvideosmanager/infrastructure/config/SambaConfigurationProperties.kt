package me.pinfort.tsvideosmanager.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "samba")
data class SambaConfigurationProperties(
    val videoStoreNas: Server,
    val originalStoreNas: Server
) {
    data class Server(
        val url: String,
        val username: String,
        val password: String
    )
}
