package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.springframework.stereotype.Component

@Component
class ProgramCommand(
    private val programMapper: ProgramMapper,
    private val programConverter: ProgramConverter,
    private val createdFileMapper: CreatedFileMapper,
    private val createdFileConverter: CreatedFileConverter,
) {
    fun selectByName(name: String, limit: Int = 100, offset: Int = 0): List<Program> {
        val programs: List<ProgramDto> = programMapper.selectByName(name, limit, offset)

        return programs.map { programConverter.convert(it) }
    }

    fun find(id: Int): Program? {
        return programMapper.find(id)?.let { programConverter.convert(it) }
    }

    fun videoFiles(program: Program): List<CreatedFile> {
        return createdFileMapper.selectByExecutedFileId(program.executedFileId).map { createdFileConverter.convert(it) }
    }
}
