package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDetailDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import javax.sql.DataSource

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@SpringJUnitConfig
class ProgramDetailMapperTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var programDetailMapper: ProgramDetailMapper

    @BeforeEach
    fun setup() {
    }

    @Nested
    inner class FindTest {
        @Test
        fun success() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM executed_file").execute()
            connection.prepareStatement("DELETE FROM program").execute()

            connection.prepareStatement(
                """
                    INSERT INTO executed_file(id,file,drops,`size`,recorded_at,channel,title,channelName,duration,status)
                    VALUES(1,'filepath',0,2,cast('2009-08-03 23:58:01' as datetime),'BSxx','myTitle','myChannel',3,'SPLITTED');
                """.trimIndent()
            ).execute()
            connection.prepareStatement(
                """
                    INSERT INTO program(id,name,executed_file_id,status)
                    VALUES(1,'myName',1,'COMPLETED');
                """.trimIndent()
            ).execute()

            connection.commit()

            val actual = programDetailMapper.find(1)
            connection.close()

            Assertions.assertThat(actual).isEqualTo(
                ProgramDetailDto(
                    id = 1,
                    name = "myName",
                    executedFileId = 1,
                    status = ProgramDto.Status.COMPLETED,
                    drops = 0,
                    size = 2,
                    recordedAt = LocalDateTime.of(2009, 8, 3, 23, 58, 1),
                    channel = "BSxx",
                    title = "myTitle",
                    channelName = "myChannel",
                    duration = 3.0
                )
            )
        }

        @Test
        fun successWithExecutedFileNull() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM executed_file").execute()
            connection.prepareStatement("DELETE FROM program").execute()

            connection.prepareStatement(
                """
                    INSERT INTO program(id,name,executed_file_id,status)
                    VALUES(1,'myName',1,'COMPLETED');
                """.trimIndent()
            ).execute()

            connection.commit()

            val actual = programDetailMapper.find(1)
            connection.close()

            Assertions.assertThat(actual).isEqualTo(
                ProgramDetailDto(
                    id = 1,
                    name = "myName",
                    executedFileId = 1,
                    status = ProgramDto.Status.COMPLETED,
                    drops = null,
                    size = null,
                    recordedAt = null,
                    channel = null,
                    title = null,
                    channelName = null,
                    duration = null
                )
            )
        }
    }
}
