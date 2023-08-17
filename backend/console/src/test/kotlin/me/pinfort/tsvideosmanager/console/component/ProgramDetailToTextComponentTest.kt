package me.pinfort.tsvideosmanager.console.component

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import me.pinfort.tsvideosmanager.infrastructure.structs.CreatedFile
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import me.pinfort.tsvideosmanager.infrastructure.structs.ProgramDetail
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProgramDetailToTextComponentTest {
    @InjectMockKs
    private lateinit var programDetailToTextComponent: ProgramDetailToTextComponent

    private val programDetail = ProgramDetail(
        id = 1,
        name = "name",
        executedFileId = 2,
        status = Program.Status.COMPLETED,
        drops = 2,
        size = 3,
        recordedAt = LocalDateTime.of(2023, 1, 1, 0, 0, 0),
        channel = "channel",
        title = "title",
        channelName = "channelName",
        duration = 60.0,
        createdFiles = listOf(
            CreatedFile(
                id = 1,
                mime = "mime",
                file = "file",
                splittedFileId = 2,
                size = 3,
                encoding = "encoding",
                status = CreatedFile.Status.FILE_MOVED
            )
        )
    )

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Nested
    inner class ConvertConsoleTest {
        @Test
        fun success() {
            val actual = programDetailToTextComponent.convertConsole(programDetail)
            val expected = """
                番組ID: 1
                番組名: title
                放送局: channelName
                放送日時: 2023/01/01 00:00:00
                放送時間: 1m
                ファイル: 1件
                id	mime	name
                1	mime	file
                
            """.trimIndent()
            Assertions.assertThat(actual).isEqualTo(expected)
        }
    }
}
