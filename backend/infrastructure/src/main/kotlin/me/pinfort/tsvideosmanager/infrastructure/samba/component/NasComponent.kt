package me.pinfort.tsvideosmanager.infrastructure.samba.component

import jcifs.SmbResource
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import org.springframework.stereotype.Component

@Component
class NasComponent(
    private val sambaClient: SambaClient
) {
    private val videoStoreNas = sambaClient.videoStoreNas()
    private val originalStoreNas = sambaClient.originalStoreNas()

    fun getResource(file: String): SmbResource {
        val resource = videoStoreNas.resolve(file.replace('\\', '/'))
        if (resource.exists()) return resource
        val originalResource = originalStoreNas.resolve(file.replace('\\', '/'))
        if (originalResource.exists()) return originalResource
        throw Exception("File not found, path=$file")
    }

    fun deleteResource(file: String): SambaClient.NasType {
        val resource = videoStoreNas.resolve(file.replace('\\', '/'))
        if (resource.exists()) {
            resource.delete()
            return SambaClient.NasType.VIDEO_STORE_NAS
        }
        val originalResource = originalStoreNas.resolve(file.replace('\\', '/'))
        if (originalResource.exists()) {
            originalResource.delete()
            return SambaClient.NasType.ORIGINAL_STORE_NAS
        }
        throw Exception("File not found, path=$file")
    }
}
