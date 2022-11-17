package me.pinfort.tsvideosmanager.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "samba")
data class SambaConfigurationProperties(
    val videoStoreNas: Server,
) {
    data class Server(
        val url: String,
        val username: String,
        val password: String,
    )
}
