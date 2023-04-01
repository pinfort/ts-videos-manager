package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.springframework.stereotype.Component

@Component
class ProgramConverter(
    private val programStatusConverter: ProgramStatusConverter,
) {
    fun convert(dto: ProgramDto): Program {
        return Program(
            id = dto.id,
            name = dto.name,
            executedFileId = dto.executedFileId,
            status = programStatusConverter.convert(dto.status),
            drops = dto.drops,
        )
    }
}
