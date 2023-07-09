package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.SplittedFileDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface SplittedFileMapper {
    @Select(
        """
        SELECT
            id,
            executed_file_id,
            file,
            size,
            duration,
            status
        FROM
            splitted_file
        WHERE
            executed_file_id = #{executedFileId}
    """
    )
    fun selectByExecutedFileId(executedFileId: Int): List<SplittedFileDto>
}
