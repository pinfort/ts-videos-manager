package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ExecutedFileDto
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import org.springframework.stereotype.Component

@Component
class ExecutedFileConverter(
    val executedFileStatusConverter: ExecutedFileStatusConverter,
) {
    fun convert(dto: ExecutedFileDto): ExecutedFile {
        return ExecutedFile(
            id = dto.id,
            file = dto.file,
            drops = dto.drops,
            size = dto.size,
            recordedAt = dto.recordedAt,
            channel = dto.channel,
            title = dto.title,
            channelName = dto.channelName,
            duration = dto.duration,
            status = executedFileStatusConverter.convert(dto.status),
        )
    }
}
