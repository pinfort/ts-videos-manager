package me.pinfort.tsvideosmanager.infrastructure.database.dto.converter

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import me.pinfort.tsvideosmanager.infrastructure.database.dto.ProgramDto
import me.pinfort.tsvideosmanager.infrastructure.structs.Program
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProgramConverterTest {
    @MockK
    private lateinit var programStatusConverter: ProgramStatusConverter

    @InjectMockKs
    private lateinit var programConverter: ProgramConverter

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun success() {
        every { programStatusConverter.convert(any()) } returns Program.Status.COMPLETED

        val actual = programConverter.convert(
            ProgramDto(
                id = 1,
                name = "2",
                executedFileId = 3,
                status = ProgramDto.Status.COMPLETED,
                drops = 4,
                size = 5,
                recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                channel = "channel",
                title = "title",
                channelName = "channelName",
                duration = 6.0
            )
        )

        Assertions.assertThat(actual).isEqualTo(
            Program(
                id = 1,
                name = "2",
                executedFileId = 3,
                status = Program.Status.COMPLETED,
                drops = 4,
                size = 5,
                recordedAt = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                channel = "channel",
                title = "title",
                channelName = "channelName",
                duration = 6.0
            )
        )
    }
}
