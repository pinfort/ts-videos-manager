package me.pinfort.tsvideosmanager.console.subcommands

import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import me.pinfort.tsvideosmanager.console.component.UserQuestionComponent
import me.pinfort.tsvideosmanager.infrastructure.command.CreatedFileCommand
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.samba.client.SambaClient
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCli::class)
class DeleteTest {
    @MockK
    private lateinit var programCommand: ProgramCommand

    @MockK
    private lateinit var userQuestionComponent: UserQuestionComponent

    @MockK
    private lateinit var createdFileCommand: CreatedFileCommand

    @InjectMockKs
    private lateinit var delete: Delete

    private val dummyProgram = Program(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.REGISTERED,
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.MIN,
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0
    )

    private val dummyCreatedFile = CreatedFile(
        id = 1,
        splittedFileId = 2,
        file = "path",
        size = 3,
        mime = "video/vnd.dlna.mpeg-tts",
        encoding = "encoding",
        status = CreatedFile.Status.REGISTERED
    )

    private val dummyProgramDetail = ProgramDetail(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.REGISTERED,
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.MIN,
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0,
        createdFiles = listOf(
            dummyCreatedFile
        )
    )

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Nested
    inner class ExecuteTest {
        @Nested
        inner class ProgramTest {
            @Test
            fun success() {
                every { userQuestionComponent.askDefaultFalse(any()) } returns true
                every { programCommand.delete(any()) } just Runs
                every { programCommand.find(any()) } returns dummyProgram

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(delete)

                parser.parse(arrayOf("delete", "program", "1"))

                verify(exactly = 1) {
                    programCommand.delete(dummyProgram)
                }
            }

            @Test
            fun canceled() {
                every { userQuestionComponent.askDefaultFalse(any()) } returns false
                every { programCommand.find(any()) } returns dummyProgram

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(delete)

                parser.parse(arrayOf("delete", "program", "1"))

                verify(exactly = 0) {
                    programCommand.delete(dummyProgram)
                }
            }
        }

        @Nested
        inner class TsFilesTest {
            @Test
            fun success() {
                every { userQuestionComponent.askDefaultFalse(any()) } returns true
                every { programCommand.findDetail(any()) } returns dummyProgramDetail
                every { createdFileCommand.delete(any()) } returns SambaClient.NasType.VIDEO_STORE_NAS

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(delete)

                parser.parse(arrayOf("delete", "ts_files", "1"))

                verify(exactly = 1) {
                    createdFileCommand.delete(dummyCreatedFile)
                }
            }

            @Test
            fun canceled() {
                every { userQuestionComponent.askDefaultFalse(any()) } returns false
                every { programCommand.findDetail(any()) } returns dummyProgramDetail

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(delete)

                parser.parse(arrayOf("delete", "ts_files", "1"))

                verify(exactly = 0) {
                    createdFileCommand.delete(dummyCreatedFile)
                }
            }
        }
    }
}
