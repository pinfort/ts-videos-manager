package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ProgramDetailConverter(
    private val programStatusConverter: ProgramStatusConverter,
    private val createdFileConverter: CreatedFileConverter
) {
    fun convert(dto: ProgramDto, createdFiles: List<CreatedFileDto>): ProgramDetail {
        return ProgramDetail(
            id = dto.id,
            name = dto.name,
            executedFileId = dto.executedFileId,
            status = programStatusConverter.convert(dto.status),
            drops = dto.drops ?: -1,
            size = dto.size ?: 0,
            recordedAt = dto.recordedAt ?: LocalDateTime.MIN,
            channel = dto.channel ?: "",
            title = dto.title ?: "",
            channelName = dto.channelName ?: "",
            duration = dto.duration ?: -1.0,
            createdFiles = createdFiles.map { createdFileConverter.convert(it) }
        )
    }
}
