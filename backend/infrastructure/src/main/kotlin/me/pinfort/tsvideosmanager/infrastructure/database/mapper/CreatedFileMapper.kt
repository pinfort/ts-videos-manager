package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface CreatedFileMapper {
    @Select(
        """
            SELECT
                id,
                splitted_file_id,
                file,
                size,
                mime,
                encoding,
                status
            FROM
                created_file
            WHERE
                id = #{id}
        """
    )
    fun find(id: Int): CreatedFileDto?

    @Select(
        """
            SELECT
                id,
                splitted_file_id,
                file,
                size,
                mime,
                encoding,
                status
            FROM
                created_file
            WHERE
                splitted_file_id = #{splittedFileId}
        """
    )
    fun selectBySplittedFileId(splittedFileId: Int): List<CreatedFileDto>
}
