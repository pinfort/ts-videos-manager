package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import org.apache.ibatis.annotations.Delete
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
    fun find(id: Long): CreatedFileDto?

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
    fun selectBySplittedFileId(splittedFileId: Long): List<CreatedFileDto>

    @Select(
        """
            SELECT
                created_file.id,
                created_file.splitted_file_id,
                created_file.file,
                created_file.size,
                created_file.mime,
                created_file.encoding,
                created_file.status
            FROM
                created_file
            LEFT JOIN splitted_file
            ON created_file.splitted_file_id = splitted_file.id
            WHERE splitted_file.executed_file_id = #{executedFileId}
        """
    )
    fun selectByExecutedFileId(executedFileId: Long): List<CreatedFileDto>

    @Delete(
        """
            DELETE FROM
                created_file
            WHERE
                id = #{id}
        """
    )
    fun delete(id: Long)
}
