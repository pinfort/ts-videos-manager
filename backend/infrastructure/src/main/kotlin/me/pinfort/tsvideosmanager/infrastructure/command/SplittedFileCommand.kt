package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.mapper.SplittedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.SplittedFile
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class SplittedFileCommand(
    private val splittedFileMapper: SplittedFileMapper,
    private val logger: Logger
) {
    fun delete(splittedFile: SplittedFile) {
        splittedFileMapper.delete(splittedFile.id.toLong())
        logger.info("Delete splitted file, id=${splittedFile.id}")
    }
}
