package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.springframework.stereotype.Component

@Component
class ProgramCommand(
    private val programMapper: ProgramMapper,
    private val programConverter: ProgramConverter,
) {
    fun selectByName(name: String, limit: Int = 100, offset: Int = 0): List<Program> {
        val programs: List<ProgramDto> = programMapper.selectByName(name, limit, offset)

        return programs.map { programConverter.convert(it) }
    }

    fun find(id: Int): Program? {
        return programMapper.find(id)?.let { programConverter.convert(it) }
    }
}
