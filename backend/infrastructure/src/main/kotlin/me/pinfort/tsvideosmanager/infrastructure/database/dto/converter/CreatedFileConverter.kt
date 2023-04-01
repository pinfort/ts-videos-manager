package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.springframework.stereotype.Component

@Component
class CreatedFileConverter(
    private val createdFileStatusConverter: CreatedFileStatusConverter
) {
    fun convert(dto: CreatedFileDto): CreatedFile {
        return CreatedFile(
            id = dto.id,
            splittedFileId = dto.splittedFileId,
            file = dto.file,
            size = dto.size,
            mime = dto.mime,
            encoding = dto.encoding,
            status = createdFileStatusConverter.convert(dto.status)
        )
    }
}
