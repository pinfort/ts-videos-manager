package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.verifySequence
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDetailDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramDetailConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramDetailMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
    private lateinit var programDetailMapper: ProgramDetailMapper

    @MockK
    private lateinit var programDetailConverter: ProgramDetailConverter

    @InjectMockKs
    private lateinit var programCommand: ProgramCommand

    private val program = Program(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3
    )
    private val programDto = ProgramDto(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = ProgramDto.Status.COMPLETED,
        drops = 3
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
    val programDetailDto = ProgramDetailDto(
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
            every { programDetailMapper.find(any()) } returns programDetailDto
            every { programDetailConverter.convert(any(), any()) } returns programDetail
            every { createdFileMapper.selectByExecutedFileId(any()) } returns listOf(createdFileDto)

            val actual = programCommand.findDetail(1)

            Assertions.assertThat(actual).isEqualTo(programDetail)

            verifySequence {
                programDetailMapper.find(1)
                programDetailConverter.convert(programDetailDto, listOf(createdFileDto))
            }
        }

        @Test
        fun noHit() {
            every { programDetailMapper.find(any()) } returns null

            val actual = programCommand.findDetail(1)

            Assertions.assertThat(actual).isNull()

            verifySequence {
                programDetailMapper.find(1)
            }
        }
    }
}
