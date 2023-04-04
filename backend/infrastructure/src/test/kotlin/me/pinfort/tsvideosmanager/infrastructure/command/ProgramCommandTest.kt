package me.pinfort.tsvideosmanager.infrastructure.command

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import me.pinfort.tsvideosmanager.infrastructure.database.dto.CreatedFileDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.CreatedFileConverter
import me.pinfort.tsvideosmanager.infrastructure.database.dto.converter.ProgramConverter
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.CreatedFileMapper
import me.pinfort.tsvideosmanager.infrastructure.database.mapper.ProgramMapper
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProgramCommandTest {
    @MockK
    private lateinit var programMapper: ProgramMapper
    @MockK
    private lateinit var programConverter: ProgramConverter
    @MockK
    private lateinit var createdFileMapper: CreatedFileMapper
    @MockK
    private lateinit var createdFileConverter: CreatedFileConverter
    @InjectMockKs
    private lateinit var programCommand: ProgramCommand

    private val program = Program(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 3,
    )
    private val programDto = ProgramDto(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = ProgramDto.Status.COMPLETED,
        drops = 3,
    )
    private val createdFileDto = CreatedFileDto(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFileDto.Status.ENCODE_SUCCESS,
    )
    private val createdFile = CreatedFile(
        id = 1,
        splittedFileId = 2,
        file = "file",
        size = 3,
        mime = "mime",
        encoding = "encoding",
        status = CreatedFile.Status.ENCODE_SUCCESS,
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
                executedFileId = 1,
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
                executedFileId = 1,
            )

            val actual = programCommand.videoFiles(testProgram)

            Assertions.assertThat(actual).isEmpty()

            verifySequence {
                createdFileMapper.selectByExecutedFileId(1)
            }
        }
    }
}
