package me.pinfort.tsvideosmanager.infrastructure.command

import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ExecutedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ExecutedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import org.springframework.stereotype.Component

@Component
class ExecutedFileCommand(
    private val executedFileMapper: ExecutedFileMapper,
    private val executedFileConverter: ExecutedFileConverter,
) {
    fun find(id: Int): ExecutedFile? {
        return executedFileMapper.find(id)?.let { executedFileConverter.convert(it) }
    }
}
