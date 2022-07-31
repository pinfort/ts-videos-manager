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
            ex.drops
        FROM
            program pg
            LEFT OUTER JOIN executed_file ex
                ON pg.executed_file_id = ex.id
        WHERE
            pg.name LIKE CONCAT('%', #{name}, '%')
        LIMIT
            #{limit}
        OFFSET
            #{offset}
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
                ex.drops
            FROM
                program pg
                LEFT OUTER JOIN executed_file ex
                    ON pg.executed_file_id = ex.id
            WHERE
                pg.id = #{id}
        """
    )
    fun find(id: Int): ProgramDto?

    @Delete(
        """
            DELETE FROM
                program
            WHERE
                id = #{id}
        """
    )
    fun deleteById(id: Int)
}
