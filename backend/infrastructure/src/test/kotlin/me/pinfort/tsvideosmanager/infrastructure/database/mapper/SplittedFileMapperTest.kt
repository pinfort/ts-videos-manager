package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.SplittedFileDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
@SpringJUnitConfig
class SplittedFileMapperTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var splittedFileMapper: SplittedFileMapper

    @BeforeEach
    fun setup() {
    }

    @Nested
    inner class SelectByExecutedFileIdTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM splitted_file").execute()
            connection.prepareStatement(
                """
                INSERT INTO splitted_file(id,executed_file_id,file,size,duration,status) VALUES(1,1,'filepath',2,3,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO splitted_file(id,executed_file_id,file,size,duration,status) VALUES(2,2,'filepath2',2,3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = splittedFileMapper.selectByExecutedFileId(1)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(1)

            Assertions.assertThat(actual[0]).isEqualTo(
                SplittedFileDto(
                    id = 1,
                    executedFileId = 1,
                    file = "filepath",
                    size = 2,
                    duration = 3.0,
                    status = SplittedFileDto.Status.REGISTERED
                )
            )
        }

        @Test
        fun multiple() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM splitted_file").execute()
            connection.prepareStatement(
                """
                INSERT INTO splitted_file(id,executed_file_id,file,size,duration,status) VALUES(1,1,'filepath',2,3,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO splitted_file(id,executed_file_id,file,size,duration,status) VALUES(2,1,'filepath2',2,3,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO splitted_file(id,executed_file_id,file,size,duration,status) VALUES(3,2,'filepath3',2,3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = splittedFileMapper.selectByExecutedFileId(1)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(2)

            Assertions.assertThat(actual[0]).isEqualTo(
                SplittedFileDto(
                    id = 1,
                    executedFileId = 1,
                    file = "filepath",
                    size = 2,
                    duration = 3.0,
                    status = SplittedFileDto.Status.REGISTERED
                )
            )

            Assertions.assertThat(actual[1]).isEqualTo(
                SplittedFileDto(
                    id = 2,
                    executedFileId = 1,
                    file = "filepath2",
                    size = 2,
                    duration = 3.0,
                    status = SplittedFileDto.Status.REGISTERED
                )
            )
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM splitted_file").execute()
            connection.commit()

            val actual = splittedFileMapper.selectByExecutedFileId(1)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(0)
        }
    }
}
