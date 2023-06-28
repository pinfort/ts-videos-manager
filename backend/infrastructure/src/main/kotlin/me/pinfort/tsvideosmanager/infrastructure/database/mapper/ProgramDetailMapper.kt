package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDetailDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface ProgramDetailMapper {
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
    fun find(id: Int): ProgramDetailDto?
}
