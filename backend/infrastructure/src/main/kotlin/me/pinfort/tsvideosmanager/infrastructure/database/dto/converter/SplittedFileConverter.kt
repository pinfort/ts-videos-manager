package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.SplittedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.SplittedFile
import org.springframework.stereotype.Component

@Component
class SplittedFileConverter(
    private val convertStatus: SplittedFileStatusConverter
) {
    fun convert(dto: SplittedFileDto): SplittedFile {
        return SplittedFile(
            id = dto.id,
            executedFileId = dto.executedFileId,
            file = dto.file,
            size = dto.size,
            duration = dto.duration,
            status = convertStatus.convert(dto.status)
        )
    }
}
