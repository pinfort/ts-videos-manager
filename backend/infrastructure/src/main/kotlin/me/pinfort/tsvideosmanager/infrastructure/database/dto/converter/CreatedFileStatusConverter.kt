package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import org.springframework.stereotype.Component

@Component
class CreatedFileStatusConverter {
    fun convert(status: CreatedFileDto.Status): CreatedFile.Status {
        return when (status) {
            CreatedFileDto.Status.REGISTERED -> CreatedFile.Status.REGISTERED
            CreatedFileDto.Status.FILE_MOVED -> CreatedFile.Status.FILE_MOVED
            CreatedFileDto.Status.ENCODE_SUCCESS -> CreatedFile.Status.ENCODE_SUCCESS
        }
    }
}
