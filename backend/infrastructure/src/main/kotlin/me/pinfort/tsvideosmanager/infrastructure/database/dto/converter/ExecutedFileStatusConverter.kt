package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ExecutedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import org.springframework.stereotype.Component

@Component
class ExecutedFileStatusConverter {
    fun convert(status: ExecutedFileDto.Status): ExecutedFile.Status {
        return when(status) {
            ExecutedFileDto.Status.REGISTERED -> ExecutedFile.Status.REGISTERED
            ExecutedFileDto.Status.DROPCHECKED -> ExecutedFile.Status.DROPCHECKED
            ExecutedFileDto.Status.SPLITTED -> ExecutedFile.Status.SPLITTED
        }
    }
}
