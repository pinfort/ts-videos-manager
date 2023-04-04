package me.pinfort.tsvideosmanager.infrastructure.command

import jcifs.CIFSException
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class CreatedFileCommand(
    private val createdFileMapper: CreatedFileMapper,
    private val createdFileConverter: CreatedFileConverter,
    private val sambaClient: SambaClient
) {
    fun findMp4File(id: Int): CreatedFile? {
        val createdFile: CreatedFile = createdFileMapper.find(id)?.let { createdFileConverter.convert(it) } ?: return null
        // 動画ファイルでない場合はファイルが存在しない扱いをする
        if (!createdFile.isMp4) return null
        return createdFile
    }

    fun streamCreatedFile(id: Int): InputStream? {
        val createdFile: CreatedFile = findMp4File(id) ?: return null
        return try {
            sambaClient.videoStoreNas()
                .resolve(createdFile.file.replace('\\', '/')).openInputStream()
        } catch (e: CIFSException) {
            null
        }
    }
}
