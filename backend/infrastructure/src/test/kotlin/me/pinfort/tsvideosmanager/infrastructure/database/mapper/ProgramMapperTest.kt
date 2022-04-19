package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class ProgramMapperTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var programMapper: ProgramMapper

    @BeforeEach
    fun setup() {
    }

    @Nested
    inner class SelectByNameTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(1,'test',1,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(2,'esta',2,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(3,'aest',3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = programMapper.selectByName("test")

            Assertions.assertThat(actual.size).isEqualTo(1)

            Assertions.assertThat(actual[0]).isEqualTo(
                ProgramDto(
                    1,
                    "test",
                    1,
                    ProgramDto.Status.REGISTERED,
                )
            )
        }

        @Test
        fun multiple() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(1,'test',1,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(2,'atest',2,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(3,'testa',3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = programMapper.selectByName("test")

            Assertions.assertThat(actual.size).isEqualTo(3)

            Assertions.assertThat(actual[0]).isEqualTo(
                ProgramDto(
                    1,
                    "test",
                    1,
                    ProgramDto.Status.REGISTERED,
                )
            )

            Assertions.assertThat(actual[1]).isEqualTo(
                ProgramDto(
                    2,
                    "atest",
                    2,
                    ProgramDto.Status.REGISTERED,
                )
            )

            Assertions.assertThat(actual[2]).isEqualTo(
                ProgramDto(
                    3,
                    "testa",
                    3,
                    ProgramDto.Status.REGISTERED,
                )
            )
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.commit()

            val actual = programMapper.selectByName("test")

            Assertions.assertThat(actual.size).isEqualTo(0)
        }
    }

    @Nested
    inner class FindTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(1,'test',1,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(2,'esta',2,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(3,'aest',3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = programMapper.find(1)

            Assertions.assertThat(actual).isEqualTo(
                ProgramDto(
                    1,
                    "test",
                    1,
                    ProgramDto.Status.REGISTERED,
                )
            )
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.commit()

            val actual = programMapper.find(1)

            Assertions.assertThat(actual).isNull()
        }
    }

    @Nested
    inner class DeleteByIdTest {
        @Test
        fun one() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(1,'test',1,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(2,'esta',2,'REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO program(id,name,executed_file_id,status) VALUES(3,'aest',3,'REGISTERED');
            """
            ).execute()
            connection.commit()

            programMapper.deleteById(1)

            Assertions.assertThat(programMapper.find(1)).isNull()
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM program").execute()
            connection.commit()

            programMapper.deleteById(1)

            Assertions.assertThat(programMapper.find(1)).isNull()
        }
    }
}
