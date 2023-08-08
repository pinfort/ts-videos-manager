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
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
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

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Nested
    inner class ExecuteTest {
        @Test
        fun success() {
            every { userQuestionComponent.askDefaultFalse(any()) } returns true
            every { programCommand.delete(any()) } just Runs
            every { programCommand.find(any()) } returns dummyProgram

            val parser = ArgParser("tsVideosManager")
            parser.subcommands(delete)

            parser.parse(arrayOf("delete", "1"))

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

            parser.parse(arrayOf("delete", "1"))

            verify(exactly = 0) {
                programCommand.delete(dummyProgram)
            }
        }
    }
}
