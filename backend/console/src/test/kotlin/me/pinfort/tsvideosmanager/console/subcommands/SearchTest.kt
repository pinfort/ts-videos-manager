package me.pinfort.tsvideosmanager.console.subcommands

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli
import me.pinfort.tsvideosmanager.console.component.TerminalTextColorComponent
import me.pinfort.tsvideosmanager.infrastructure.command.ProgramCommand
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCli::class)
class SearchTest {
    @MockK
    private lateinit var programCommand: ProgramCommand

    @MockK
    private lateinit var terminalTextColorComponent: TerminalTextColorComponent

    @InjectMockKs
    private lateinit var search: Search

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

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class ExecuteTest {
        @Test
        fun success() {
            every { programCommand.selectByName(any(), any(), any()) } returns listOf(dummyProgram)
            every { programCommand.hasTsFile(any()) } returns true
            every { terminalTextColorComponent.error(any()) } returns "1\t2023/01/01 00:00:00\tchannelName\t3\ttrue\ttitle"
            val parser = ArgParser("tsVideosManager")
            parser.subcommands(search)

            parser.parse(arrayOf("search", "-n", "test"))
        }
    }
}
