package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.SplittedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.SplittedFile
import org.springframework.stereotype.Component

@Component
class SplittedFileStatusConverter {
    fun convert(dto: SplittedFileDto.Status): SplittedFile.Status {
        return when (dto) {
            SplittedFileDto.Status.REGISTERED -> SplittedFile.Status.REGISTERED
            SplittedFileDto.Status.COMPRESS_SAVED -> SplittedFile.Status.COMPRESS_SAVED
            SplittedFileDto.Status.ENCODE_TASK_ADDED -> SplittedFile.Status.ENCODE_TASK_ADDED
        }
    }
}
