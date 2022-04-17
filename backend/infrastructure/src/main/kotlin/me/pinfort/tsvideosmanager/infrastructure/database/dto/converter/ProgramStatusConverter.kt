package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.springframework.stereotype.Component

@Component
class ProgramStatusConverter {
    fun convert(dto: ProgramDto.Status): Program.Status {
        return when (dto) {
            ProgramDto.Status.REGISTERED -> Program.Status.REGISTERED
            ProgramDto.Status.COMPLETED -> Program.Status.COMPLETED
            ProgramDto.Status.ERROR -> Program.Status.ERROR
        }
    }
}
