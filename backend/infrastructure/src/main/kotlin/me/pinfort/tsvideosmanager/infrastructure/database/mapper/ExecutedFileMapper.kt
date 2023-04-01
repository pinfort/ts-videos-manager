package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ExecutedFileDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ExecutedFileMapper {
    @Select(
        """
            SELECT
                id,
                file,
                drops,
                size,
                recorded_at,
                channel,
                title,
                channelName,
                duration,
                status
            FROM
                executed_file
            WHERE
                id = #{id}
        """,
    )
    fun find(id: Int): ExecutedFileDto?
}
