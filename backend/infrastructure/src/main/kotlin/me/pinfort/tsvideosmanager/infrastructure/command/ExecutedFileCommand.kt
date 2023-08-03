package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ExecutedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ExecutedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class ExecutedFileCommand(
    private val executedFileMapper: ExecutedFileMapper,
    private val executedFileConverter: ExecutedFileConverter,
    private val logger: Logger
) {
    fun find(id: Long): ExecutedFile? {
        return executedFileMapper.find(id)?.let { executedFileConverter.convert(it) }
    }

    fun delete(executedFile: ExecutedFile) {
        executedFileMapper.delete(executedFile.id)
        logger.info("Delete executed file, id=${executedFile.id}, executedFile=$executedFile")
    }
}
