package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramDetailConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.SplittedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProgramCommand(
    private val programMapper: ProgramMapper,
    private val programConverter: ProgramConverter,
    private val createdFileMapper: CreatedFileMapper,
    private val createdFileConverter: CreatedFileConverter,
    private val programDetailConverter: ProgramDetailConverter,
    private val executedFileCommand: ExecutedFileCommand,
    private val createdFileCommand: CreatedFileCommand,
    private val splittedFileMapper: SplittedFileMapper,
    private val logger: Logger
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

    fun hasTsFile(program: Program): Boolean {
        createdFileMapper.selectByExecutedFileId(program.executedFileId).forEach { if (createdFileConverter.convert(it).isTs) { return true } }
        return false
    }

    fun findDetail(id: Int): ProgramDetail? {
        val program: ProgramDto = programMapper.find(id) ?: return null
        val createdFiles: List<CreatedFileDto> = createdFileMapper.selectByExecutedFileId(program.executedFileId)
        return programDetailConverter.convert(program, createdFiles)
    }

    @Transactional
    fun delete(program: Program) {
        val executedFile = executedFileCommand.find(program.executedFileId) ?: throw Exception("ExecutedFile not found")
        val splittedFiles = splittedFileMapper.selectByExecutedFileId(executedFile.id)
        val createdFiles: List<CreatedFileDto> = createdFileMapper.selectByExecutedFileId(program.executedFileId)

        splittedFiles.forEach {
            splittedFileMapper.delete(it.id.toLong())
            logger.info("Delete splitted file, id=${it.id}")
        }

        createdFiles.forEach {
            createdFileCommand.delete(createdFileConverter.convert(it))
            logger.info("Delete created file, id=${it.id}")
        }

        executedFileCommand.delete(executedFile)
        logger.info("Delete executed file, id=${executedFile.id}")

        programMapper.deleteById(program.id)
        logger.info("Delete program, id=${program.id}")
    }
}
