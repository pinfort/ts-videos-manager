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
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.component.DirectoryNameComponent
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.time.LocalDateTime

@OptIn(ExperimentalCli::class)
class ModifyTest {
    @MockK
    private lateinit var programCommand: ProgramCommand

    @MockK
    private lateinit var directoryNameComponent: DirectoryNameComponent

    @MockK
    private lateinit var userQuestionComponent: UserQuestionComponent

    @InjectMockKs
    private lateinit var modify: Modify

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
        file = "foo/bar/baz/1.ts",
        size = 3,
        mime = "video/vnd.dlna.mpeg-tts",
        encoding = "encoding",
        status = CreatedFile.Status.REGISTERED
    )

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Nested
    inner class ExecuteTest {
        @Nested
        inner class DirectoryNameTest {
            @Test
            fun success() {
                every { programCommand.find(any()) } returns dummyProgram
                every { programCommand.videoFiles(any()) } returns listOf(dummyCreatedFile)
                every { directoryNameComponent.replaceWithGivenDirectoryName(any(), any()) } returns Path.of("newPath")
                every { userQuestionComponent.askDefaultFalse(any()) } returns true
                every { programCommand.moveCreatedFiles(any(), any(), any()) } just Runs

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(modify)

                parser.parse(arrayOf("modify", "1", "directory_name", "newDirectory"))

                verify(exactly = 1) {
                    programCommand.moveCreatedFiles(dummyProgram, "newDirectory", false)
                }
            }

            @Test
            fun canceled() {
                every { programCommand.find(any()) } returns dummyProgram
                every { programCommand.videoFiles(any()) } returns listOf(dummyCreatedFile)
                every { directoryNameComponent.replaceWithGivenDirectoryName(any(), any()) } returns Path.of("newPath")
                every { userQuestionComponent.askDefaultFalse(any()) } returns false

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(modify)

                parser.parse(arrayOf("modify", "1", "directory_name", "newDirectory"))

                verify(exactly = 0) {
                    programCommand.moveCreatedFiles(dummyProgram, "newDirectory", false)
                }
            }
        }
    }
}
