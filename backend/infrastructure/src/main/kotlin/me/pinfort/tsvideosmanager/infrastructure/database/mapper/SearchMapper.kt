package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface SearchMapper {
    @Select("""
        SELECT
            1
        FROM
            program
    """)
    fun select(): List<ProgramDto>
}
