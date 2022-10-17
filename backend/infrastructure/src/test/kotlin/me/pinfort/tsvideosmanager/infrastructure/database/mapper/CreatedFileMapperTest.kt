package me.pinfort.tsvideosmanager.infrastructure.database.mapper

import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
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
class CreatedFileMapperTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var createdFileMapper: CreatedFileMapper

    @BeforeEach
    fun setup() {
    }

    @Nested
    inner class FindTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM created_file").execute()
            connection.prepareStatement(
                """
                INSERT INTO created_file(id,splitted_file_id,file,size,mime,encoding,status) VALUES(1,2,'test',3,'test2','test3','REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = createdFileMapper.find(1)
            connection.close()

            Assertions.assertThat(actual).isNotNull
            Assertions.assertThat(actual).isEqualTo(
                CreatedFileDto(
                    id = 1,
                    splittedFileId = 2,
                    file = "test",
                    size = 3,
                    mime = "test2",
                    encoding = "test3",
                    status = CreatedFileDto.Status.REGISTERED,
                )
            )
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM created_file").execute()
            connection.commit()

            val actual = createdFileMapper.find(1)
            connection.close()

            Assertions.assertThat(actual).isNull()
        }
    }

    @Nested
    inner class SelectBySplittedFileIdTest {
        @Test
        fun single() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM created_file").execute()
            connection.prepareStatement(
                """
                INSERT INTO created_file(id,splitted_file_id,file,size,mime,encoding,status) VALUES(1,2,'test',3,'test2','test3','REGISTERED');
            """
            ).execute()
            connection.commit()

            val actual = createdFileMapper.selectBySplittedFileId(2)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(1)
            Assertions.assertThat(actual[0]).isEqualTo(
                CreatedFileDto(
                    id = 1,
                    splittedFileId = 2,
                    file = "test",
                    size = 3,
                    mime = "test2",
                    encoding = "test3",
                    status = CreatedFileDto.Status.REGISTERED,
                )
            )
        }

        @Test
        fun multiple() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM created_file").execute()
            connection.prepareStatement(
                """
                INSERT INTO created_file(id,splitted_file_id,file,size,mime,encoding,status) VALUES(1,2,'test',3,'test2','test3','REGISTERED');
            """
            ).execute()
            connection.prepareStatement(
                """
                INSERT INTO created_file(id,splitted_file_id,file,size,mime,encoding,status) VALUES(2,2,'test4',4,'test5','test6','ENCODE_SUCCESS');
            """
            ).execute()
            connection.commit()

            val actual = createdFileMapper.selectBySplittedFileId(2)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(2)
            Assertions.assertThat(actual[0]).isEqualTo(
                CreatedFileDto(
                    id = 1,
                    splittedFileId = 2,
                    file = "test",
                    size = 3,
                    mime = "test2",
                    encoding = "test3",
                    status = CreatedFileDto.Status.REGISTERED,
                )
            )
            Assertions.assertThat(actual[1]).isEqualTo(
                CreatedFileDto(
                    id = 2,
                    splittedFileId = 2,
                    file = "test4",
                    size = 4,
                    mime = "test5",
                    encoding = "test6",
                    status = CreatedFileDto.Status.ENCODE_SUCCESS,
                )
            )
        }

        @Test
        fun none() {
            val connection = dataSource.connection
            connection.prepareStatement("DELETE FROM created_file").execute()
            connection.commit()

            val actual = createdFileMapper.selectBySplittedFileId(1)
            connection.close()

            Assertions.assertThat(actual.size).isEqualTo(0)
        }
    }
}
