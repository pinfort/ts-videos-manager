package me.pinfort.tsvideosmanager.console.subcommands

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import me.pinfort.tsvideosmanager.console.component.ProgramDetailToTextComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ExecutedFileCommand
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.structs.ExecutedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCli::class)
class GetTest {
    @MockK
    private lateinit var programCommand: ProgramCommand

    @MockK
    private lateinit var executedFileCommand: ExecutedFileCommand

    @MockK
    private lateinit var programDetailToTextComponent: ProgramDetailToTextComponent

    @InjectMockKs
    private lateinit var get: Get

    private val programDetail = ProgramDetail(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.REGISTERED,
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0,
        createdFiles = listOf()
    )

    private val executedFile = ExecutedFile(
        id = 1,
        file = "file",
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 4.0,
        status = ExecutedFile.Status.REGISTERED
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class ExecuteTest {
        @Nested
        inner class ProgramTest {
            @Test
            fun success() {
                every { programCommand.findDetail(1) } returns programDetail
                every { programDetailToTextComponent.convertConsole(any()) } returns "text"

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(get)

                parser.parse(arrayOf("get", "program", "1"))
            }

            @Test
            fun notFound() {
                every { programCommand.findDetail(1) } returns null

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(get)

                parser.parse(arrayOf("get", "program", "1"))
            }
        }

        @Nested
        inner class ExecutedFileTest {
            @Test
            fun success() {
                every { executedFileCommand.find(1) } returns executedFile

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(get)

                parser.parse(arrayOf("get", "executed_file", "1"))
            }

            @Test
            fun notFound() {
                every { executedFileCommand.find(1) } returns null

                val parser = ArgParser("tsVideosManager")
                parser.subcommands(get)

                parser.parse(arrayOf("get", "executed_file", "1"))
            }
        }
    }
}
