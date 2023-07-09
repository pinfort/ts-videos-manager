package me.pinfort.tsvideosmanager.infrastructure.samba.client

import jcifs.CIFSContext
import jcifs.config.PropertyConfiguration
import jcifs.context.BaseContext
import jcifs.smb.NtlmPasswordAuthenticator
import jcifs.smb.SmbFile
import me.pinfort.tsvideosmanager.infrastructure.config.SambaConfigurationProperties
import org.springframework.stereotype.Component
import java.util.Properties

@Component
class SambaClient(
    private val sambaConfigurationProperties: SambaConfigurationProperties
) {
    enum class NasType {
        VIDEO_STORE_NAS,
        ORIGINAL_STORE_NAS
    }

    fun videoStoreNas(): SmbFile {
        val context = cifsContext(
            sambaConfigurationProperties.videoStoreNas.username,
            sambaConfigurationProperties.videoStoreNas.password
        )
        return connect(sambaConfigurationProperties.videoStoreNas.url, context)
    }

    fun originalStoreNas(): SmbFile {
        val context = cifsContext(
            sambaConfigurationProperties.originalStoreNas.username,
            sambaConfigurationProperties.originalStoreNas.password
        )
        return connect(sambaConfigurationProperties.originalStoreNas.url, context)
    }

    private fun connect(url: String, context: CIFSContext): SmbFile {
        return SmbFile(url, context)
    }

    private fun cifsContext(username: String, password: String): CIFSContext {
        val auth = NtlmPasswordAuthenticator(
            username,
            password
        )
        return baseContext(properties()).withCredentials(auth)
    }

    private fun baseContext(properties: Properties): BaseContext {
        return BaseContext(
            PropertyConfiguration(
                properties
            )
        )
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.setProperty("jcifs.smb.client.minVersion", "SMB202")
        properties.setProperty("jcifs.smb.client.maxVersion", "SMB311")
        return properties
    }
}
