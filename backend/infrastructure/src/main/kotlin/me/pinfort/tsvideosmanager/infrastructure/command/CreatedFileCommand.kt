package me.pinfort.tsvideosmanager.infrastructure.command

import jcifs.CIFSException
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import me.pinfort.tsvideosmanager.infrastructure.samba.component.NasComponent
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.io.InputStream

@Component
class CreatedFileCommand(
    private val createdFileMapper: CreatedFileMapper,
    private val createdFileConverter: CreatedFileConverter,
    private val sambaClient: SambaClient,
    private val nasComponent: NasComponent,
    private val logger: Logger
) {
    fun findMp4File(id: Long): CreatedFile? {
        val createdFile: CreatedFile = createdFileMapper.find(id)?.let { createdFileConverter.convert(it) } ?: return null
        // 動画ファイルでない場合はファイルが存在しない扱いをする
        if (!createdFile.isMp4) return null
        return createdFile
    }

    fun streamCreatedFile(id: Long): InputStream? {
        val createdFile: CreatedFile = findMp4File(id) ?: return null
        return try {
            sambaClient.videoStoreNas()
                .resolve(createdFile.file.replace('\\', '/')).openInputStream()
        } catch (e: CIFSException) {
            null
        }
    }

    @Transactional
    fun delete(createdFile: CreatedFile, dryRun: Boolean = false): SambaClient.NasType {
        return try {
            val removedFrom = if (!dryRun) {
                createdFileMapper.delete(createdFile.id)
                nasComponent.deleteResource(createdFile.file)
            } else {
                SambaClient.NasType.VIDEO_STORE_NAS
            }
            logger.info("Delete created file, id=${createdFile.id}, createdFile=$createdFile, removedFrom=$removedFrom")
            removedFrom
        } catch (e: Exception) {
            logger.error("Failed to delete file. id=${createdFile.id}, file=${createdFile.file}, createdFile=$createdFile", e)
            // RuntimeExceptionにしないとロールバック対象にならない
            throw RuntimeException(e)
        }
    }
}
