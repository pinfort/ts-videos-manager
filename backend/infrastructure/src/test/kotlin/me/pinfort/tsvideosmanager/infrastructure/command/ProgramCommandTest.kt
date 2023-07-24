package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import io.mockk.verifySequence
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.SplittedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramDetailConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.SplittedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import java.time.LocalDateTime

class ProgramCommandTest {
    @MockK
    private lateinit var programMapper: ProgramMapper

    @MockK
    private lateinit var programConverter: ProgramConverter

    @MockK
    private lateinit var createdFileMapper: CreatedFileMapper

    @MockK
    private lateinit var createdFileConverter: CreatedFileConverter

    @MockK
    private lateinit var programDetailConverter: ProgramDetailConverter

    @MockK
    private lateinit var executedFileCommand: ExecutedFileCommand

    @MockK
    private lateinit var createdFileCommand: CreatedFileCommand

    @MockK
    private lateinit var splittedFileMapper: SplittedFileMapper

    @MockK
    private lateinit var logger: Logger

    @InjectMockKs
    private lateinit var programCommand: ProgramCommand

    private val program = Program(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3,
        size = 4,
        recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 5.0
    )
    private val programDto = ProgramDto(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = ProgramDto.Status.COMPLETED,
        drops = 3,
        size = 4,
        recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 5.0
    )
    private val createdFileDto = CreatedFileDto(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFileDto.Status.ENCODE_SUCCESS
    )
    private val createdFile = CreatedFile(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFile.Status.ENCODE_SUCCESS
    )
    private val programDetail = ProgramDetail(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3,
        size = 4,
        recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 5.0,
        createdFiles = listOf(createdFile)
    )
    val executedFile = ExecutedFile(
        id = 1,
        status = ExecutedFile.Status.REGISTERED,
        size = 2,
        recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 3.0,
        drops = 4,
        file = "file"
    )
    val splittedFileDto = SplittedFileDto(
        id = 1,
        executedFileId = 2,
        file = "file",
        size = 3,
        status = SplittedFileDto.Status.REGISTERED,
        duration = 4.0
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class SelectByName {
        @Test
        fun success() {
            every { programMapper.selectByName(any(), any(), any()) } returns listOf(programDto)
            every { programConverter.convert(any()) } returns program

            val actual = programCommand.selectByName("test", 1, 2)

            Assertions.assertThat(actual).isEqualTo(listOf(program))

            verifySequence {
                programMapper.selectByName("test", 1, 2)
                programConverter.convert(programDto)
            }
        }

        @Test
        fun noHit() {
            every { programMapper.selectByName(any(), any(), any()) } returns emptyList()

            val actual = programCommand.selectByName("test", 1, 2)

            Assertions.assertThat(actual).isEmpty()

            verifySequence {
                programMapper.selectByName("test", 1, 2)
            }
        }
    }

    @Nested
    inner class Find {
        @Test
        fun success() {
            every { programMapper.find(any()) } returns programDto
            every { programConverter.convert(any()) } returns program

            val actual = programCommand.find(1)

            Assertions.assertThat(actual).isEqualTo(program)

            verifySequence {
                programMapper.find(1)
                programConverter.convert(programDto)
            }
        }

        @Test
        fun noHit() {
            every { programMapper.find(any()) } returns null

            val actual = programCommand.find(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                programMapper.find(1)
            }
        }
    }

    @Nested
    inner class VideoFiles {
        @Test
        fun success() {
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)
            every { createdFileConverter.convert(any()) } returns createdFile

            val testProgram = program.copy(
                executedFileId = 1
            )

            val actual = programCommand.videoFiles(testProgram)

            Assertions.assertThat(actual).isEqualTo(listOf(createdFile))

            verifySequence {
                createdFileMapper.selectByExecutedFileId(1)
                createdFileConverter.convert(createdFileDto)
            }
        }

        @Test
        fun noHit() {
            every { createdFileMapper.selectByExecutedFileId(any()) } returns emptyList()

            val testProgram = program.copy(
                executedFileId = 1
            )

            val actual = programCommand.videoFiles(testProgram)

            Assertions.assertThat(actual).isEmpty()

            verifySequence {
                createdFileMapper.selectByExecutedFileId(1)
            }
        }
    }

    @Nested
    inner class HasTs {
        @Test
        fun success() {
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)
            every { createdFileConverter.convert(any()).isTs } returns true

            val actual = programCommand.hasTsFile(program)

            Assertions.assertThat(actual).isTrue
        }

        @Test
        fun successNoTs() {
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)
            every { createdFileConverter.convert(any()).isTs } returns false

            val actual = programCommand.hasTsFile(program)

            Assertions.assertThat(actual).isFalse
        }

        @Test
        fun successNoResult() {
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf()

            val actual = programCommand.hasTsFile(program)

            Assertions.assertThat(actual).isFalse

            verify(exactly = 0) {
                createdFileConverter.convert(any())
            }
        }
    }

    @Nested
    inner class FindDetailTest {
        @Test
        fun success() {
            every { programMapper.find(any()) } returns programDto
            every { programDetailConverter.convert(any(), any()) } returns programDetail
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)

            val actual = programCommand.findDetail(1)

            Assertions.assertThat(actual).isEqualTo(programDetail)

            verifySequence {
                programMapper.find(1)
                programDetailConverter.convert(programDto, listOf(createdFileDto))
            }
        }

        @Test
        fun noHit() {
            every { programMapper.find(any()) } returns null

            val actual = programCommand.findDetail(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                programMapper.find(1)
            }
        }
    }

    @Nested
    inner class DeleteTest {
        @Test
        fun success() {
            every { executedFileCommand.find(any()) } returns executedFile
            every { splittedFileMapper.selectByExecutedFileId(any()) } returns listOf(splittedFileDto)
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)
            every { splittedFileMapper.delete(any()) } just Runs
            every { logger.info(any()) } just Runs
            every { createdFileCommand.delete(any()) } returns SambaClient.NasType.ORIGINAL_STORE_NAS
            every { createdFileConverter.convert(any()) } returns createdFile
            every { executedFileCommand.delete(any()) } just Runs
            every { programMapper.deleteById(any()) } just Runs

            programCommand.delete(program)

            verifySequence {
                executedFileCommand.find(program.executedFileId)
                splittedFileMapper.selectByExecutedFileId(executedFile.id)
                createdFileMapper.selectByExecutedFileId(program.executedFileId)
                splittedFileMapper.delete(splittedFileDto.id.toLong())
                logger.info(any())
                createdFileConverter.convert(createdFileDto)
                createdFileCommand.delete(createdFile)
                executedFileCommand.delete(executedFile)
                programMapper.deleteById(program.id)
                logger.info(any())
            }
        }

        @Test
        fun executedNotFound() {
            every { executedFileCommand.find(any()) } throws Exception("not found")

            Assertions.assertThatThrownBy {
                programCommand.delete(program)
            }
                .hasMessage("not found")
                .isInstanceOf(Exception::class.java)

            verifySequence {
                executedFileCommand.find(program.executedFileId)
            }
        }
    }
}
