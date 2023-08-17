package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ProgramMapper {
    @Select(
        """
        SELECT
            pg.id,
            pg.name,
            pg.executed_file_id,
            pg.status,
            ex.drops,
            ex.size,
            ex.recorded_at,
            ex.channel,
            ex.title,
            ex.channelName,
            ex.duration
        FROM
            program pg
            LEFT OUTER JOIN executed_file ex
                ON pg.executed_file_id = ex.id
        WHERE
            pg.name COLLATE utf8mb4_unicode_ci LIKE CONCAT('%', #{name}, '%')
        LIMIT
            #{limit}
        OFFSET
            #{offset}
        ORDER BY recorded_at DESC
    """
    )
    fun selectByName(name: String, limit: Int = 100, offset: Int = 0): List<ProgramDto>

    @Select(
        """
            SELECT
                pg.id,
                pg.name,
                pg.executed_file_id,
                pg.status,
                ex.drops,
                ex.size,
                ex.recorded_at,
                ex.channel,
                ex.title,
                ex.channelName,
                ex.duration
            FROM
                program pg
                LEFT OUTER JOIN executed_file ex
                    ON pg.executed_file_id = ex.id
            WHERE
                pg.id = #{id}
        """
    )
    fun find(id: Long): ProgramDto?

    @Delete(
        """
            DELETE FROM
                program
            WHERE
                id = #{id}
        """
    )
    fun deleteById(id: Long)
}
