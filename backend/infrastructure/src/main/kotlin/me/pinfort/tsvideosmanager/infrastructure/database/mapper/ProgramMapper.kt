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
            id,
            name,
            executed_file_id,
            status
        FROM
            program
        WHERE
            name LIKE '%' || #{name} || '%'
    """
    )
    fun selectByName(name: String): List<ProgramDto>

    @Select(
        """
            SELECT
                id,
                name,
                executed_file_id,
                status
            FROM
                program
            WHERE
                id = #{id}
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
