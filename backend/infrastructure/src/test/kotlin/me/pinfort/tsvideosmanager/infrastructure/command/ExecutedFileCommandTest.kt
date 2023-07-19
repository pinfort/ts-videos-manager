package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import io.mockk.verifySequence
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ExecutedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ExecutedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ExecutedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import java.time.LocalDateTime

class ExecutedFileCommandTest {
    @MockK
    private lateinit var executedFileMapper: ExecutedFileMapper

    @MockK
    private lateinit var executedFileConverter: ExecutedFileConverter

    @MockK
    private lateinit var logger: Logger

    @InjectMockKs
    private lateinit var executedFileCommand: ExecutedFileCommand

    private val executedFileDto = ExecutedFileDto(
        id = 1,
        file = "file",
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.MIN,
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0,
        status = ExecutedFileDto.Status.SPLITTED
    )

    private val executedFile = ExecutedFile(
        id = 1,
        file = "file",
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.MIN,
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0,
        status = ExecutedFile.Status.SPLITTED
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class FindTest {
        @Test
        fun success() {
            every { executedFileMapper.find(any()) } returns executedFileDto
            every { executedFileConverter.convert(any()) } returns executedFile

            val actual = executedFileCommand.find(1)

            Assertions.assertThat(actual).isEqualTo(executedFile)

            verifySequence {
                executedFileMapper.find(1)
                executedFileConverter.convert(executedFileDto)
            }
        }

        @Test
        fun isNull() {
            every { executedFileMapper.find(any()) } returns null

            val actual = executedFileCommand.find(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                executedFileMapper.find(1)
            }
            verify(exactly = 0) {
                executedFileConverter.convert(any())
            }
        }
    }

    @Nested
    inner class DeleteTest {
        @Test
        fun success() {
            val executedFile = ExecutedFile(
                id = 1,
                file = "file",
                drops = 2,
                size = 3,
                recordedAt = LocalDateTime.MIN,
                channel = "channel",
                title = "title",
                channelName = "channelName",
                duration = 4.0,
                status = ExecutedFile.Status.SPLITTED
            )
            every { executedFileMapper.delete(any()) } just Runs
            every { logger.info(any()) } just Runs

            executedFileCommand.delete(executedFile)

            verifySequence {
                executedFileMapper.delete(1)
                logger.info(any())
            }
        }
    }
}
