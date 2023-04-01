package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ExecutedFileDto
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
class ExecutedFileMapperTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var executedFileMapper: ExecutedFileMapper

    @BeforeEach
    fun setup() {
    }

    @Nested
    inner class FindTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM executed_file").execute()

            connection.prepareStatement(
                """
                    INSERT INTO executed_file(id,file,drops,`size`,recorded_at,channel,title,channelName,duration,status)
                    VALUES(1,'filepath',0,2,cast('2009-08-03 23:58:01' as datetime),'BSxx','myTitle','myChannel',3,'SPLITTED');
                """.trimIndent()
            ).execute()
            connection.commit()

            val actual = executedFileMapper.find(1)
            connection.close()

            Assertions.assertThat(actual).isEqualTo(
                ExecutedFileDto(
                    id = 1,
                    file = "filepath",
                    drops = 0,
                    size = 2,
                    recordedAt = LocalDateTime.of(2009, 8, 3, 23, 58, 1),
                    channel = "BSxx",
                    title = "myTitle",
                    channelName = "myChannel",
                    duration = 3.0,
                    status = ExecutedFileDto.Status.SPLITTED
                )
            )
        }

        @Test
        fun none() {
            @Test
            fun none() {
                val connection = dataSource.connection
                connection.prepareStatement("DELETE FROM executed_file").execute()
                connection.commit()

                val actual = executedFileMapper.find(1)
                connection.close()

                Assertions.assertThat(actual).isNull()
            }
        }
    }
}
