package me.pinfort.tsvideosmanager.infrastructure.samba.component

import jcifs.SmbResource
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import org.slf4j.Logger
import org.springframework.stereotype.Component
import java.nio.file.Path

@Component
class NasComponent(
    private val sambaClient: SambaClient,
    private val logger: Logger
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

    fun moveResource(oldFile: String, newFile: String): SambaClient.NasType {
        val resource = videoStoreNas.resolve(oldFile.replace('\\', '/'))
        if (resource.exists()) {
            createDirectory(newFile, SambaClient.NasType.VIDEO_STORE_NAS)
            resource.copyTo(videoStoreNas.resolve(newFile.replace('\\', '/')))
            resource.delete()
            logger.info("Move file, oldFile=$oldFile, newFile=$newFile")
            return SambaClient.NasType.VIDEO_STORE_NAS
        }
        val originalResource = originalStoreNas.resolve(oldFile.replace('\\', '/'))
        if (originalResource.exists()) {
            createDirectory(newFile, SambaClient.NasType.ORIGINAL_STORE_NAS)
            originalResource.copyTo(originalStoreNas.resolve(newFile.replace('\\', '/')))
            originalResource.delete()
            logger.info("Move file, oldFile=$oldFile, newFile=$newFile")
            return SambaClient.NasType.ORIGINAL_STORE_NAS
        }
        throw Exception("File not found, path=$oldFile")
    }

    fun createDirectory(file: String, nasType: SambaClient.NasType) {
        val directory = Path.of(file.replace('\\', '/')).parent.toString()
        when (nasType) {
            SambaClient.NasType.VIDEO_STORE_NAS -> videoStoreNas.resolve(directory.replace('\\', '/')).mkdirs()
            SambaClient.NasType.ORIGINAL_STORE_NAS -> originalStoreNas.resolve(directory.replace('\\', '/')).mkdirs()
        }
    }
}
